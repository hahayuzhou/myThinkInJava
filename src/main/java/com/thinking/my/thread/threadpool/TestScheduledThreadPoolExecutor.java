package com.thinking.my.thread.threadpool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 测试定时调度
 * @Author liyong
 * @Date 2020/6/23 3:07 下午
 **/
public class TestScheduledThreadPoolExecutor {
    private static ScheduledThreadPoolExecutor timeoutChecker = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {
        for(int i = 0;i<10;i++){
            QueryTimeout timeout = new QueryTimeout();
            //延迟到超时时间调用 超时逻辑
            timeoutChecker.schedule(timeout, timeout.getTimeout(), TimeUnit.MILLISECONDS);
            System.out.println(i);
        }

    }


    static class QueryTimeout implements Runnable {
        public QueryTimeout() {
        }

        public void run() {
            try {
                System.out.println("11111");
            } catch (Exception e) {
            }
        }


        public long getTimeout() {
            return 10*1000L;
        }

    }

}
