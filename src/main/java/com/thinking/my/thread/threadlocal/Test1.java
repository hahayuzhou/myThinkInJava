package com.thinking.my.thread.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class Test1 {

   static ThreadLocal<Boolean> booleanThreadLocal = new ThreadLocal<>();
   static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
   static ThreadLocal<Map<String,Object>> mapThreadLocal = new ThreadLocal<>();
    static ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        stringThreadLocal.set(Thread.currentThread().getName());
        for(int i=0;i<5;i++){
            Thread thread = new Thread(()->{
                booleanThreadLocal.set(true);
                stringThreadLocal.set(Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println( Thread.currentThread().getName()+"--"+booleanThreadLocal.get());
                System.out.println( Thread.currentThread().getName()+"--"+stringThreadLocal.get());
            });
            thread.start();
            System.out.println( thread.getPriority());
        }


        System.out.println( Thread.currentThread().getName()+"--"+stringThreadLocal.get());


    }



}
