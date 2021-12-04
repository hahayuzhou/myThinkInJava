package com.thinking.my.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description
 * @Author liyong
 * @Date 2021/7/2 2:29 下午
 **/
public class LambdaThread {

    public static void main(String[] args) {
        new Thread(() -> System.out.println("--" + "aaa")).start();
       ExecutorService executor = Executors.newFixedThreadPool(10);

//        executor.submit(() -> aiCollectionFacade.initAiCollection(dto));
        Future<?> result = executor.submit(() -> sum(1, 2));

        ConcurrentHashMap concurrentHashMap  = new ConcurrentHashMap();
        concurrentHashMap.put("ss","ss");
        concurrentHashMap.get("ss");

    }

    private static void sum(int i, int i1) {

    }
}
