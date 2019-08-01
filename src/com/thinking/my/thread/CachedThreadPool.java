package com.thinking.my.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liyong on 2017/2/14.
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        Map map = new HashMap();
    }
}
