package com.thinking.my.algorithm.filterflow;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/15 11:28 上午
 **/
public class MyFilterFlow1 {
    private static Map<String, Queue<Long>> queryRecords = new ConcurrentHashMap<>();

    public static void init() {
        Timer timer = new Timer("query records gc timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    long current = System.currentTimeMillis();
                    for (Queue<Long> records : queryRecords.values()) {
                        Iterator<Long> it = records.iterator();
                        while (it.hasNext()) {
                            Long time = it.next();
                            if (current - time > 1000) {
                                it.remove();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, 1000, 1000);
    }
    /**
     * 流量控制
     * @param key
     * @return
     * @throws Throwable
     */
    public boolean filterFlow(String key) throws Throwable {

        double limitQPS = 100;
        Queue<Long> queries = getQueries(key);

        long current = System.currentTimeMillis();
        int qps = 0;
        Iterator<Long> it = queries.iterator();
        while (it.hasNext()) {
            Long time = it.next();
            if (current - time <= 1000) qps++;
        }
        if (qps >= limitQPS) {

            return false;
        }
        queries.add(System.currentTimeMillis());
        return true;
    }

    private Queue<Long> getQueries(String key) {
        Queue<Long> queries = queryRecords.get(key);
        if(queries==null){
            synchronized (queryRecords) {
                queries = queryRecords.get(key);
                if (queries == null) {
                    queries = new ConcurrentLinkedQueue<>();
                    queryRecords.put(key, queries);
                }
            }
        }
        return queries;
    }

}
