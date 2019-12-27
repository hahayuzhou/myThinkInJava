package com.thinking.my.concurrent.synchronizedtest;

/**
 * @Author:liyong40
 * @Date:2019/12/25
 */
public class SynchronizedDemo implements Runnable{

    private static int count = 0;

    private Object a = new Object();
    private static Object b = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new SynchronizedDemo());
            thread.start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + count);
    }

//    @Override
//    public void run() {
//        for (int i = 0; i < 1000000; i++)
//            count++;
//    }


//    @Override
//    public void run() {
//        //锁类对象
//        synchronized (SynchronizedDemo.class) {
//            for (int i = 0; i < 1000000; i++)
//                count++;
//        }
//    }
//    @Override
//    public void run() {
//        //锁成员对象
//        synchronized (a) {
//            for (int i = 0; i < 1000000; i++)
//                count++;
//        }
//    }
    @Override
    public void run() {
        //锁静态 对象
        synchronized (b) {
            for (int i = 0; i < 1000000; i++)
                count++;
        }
    }
//    @Override
//    public void run() {
//        //锁类成员对象
//        synchronized (this) {
//            for (int i = 0; i < 1000000; i++)
//                count++;
//        }
//    }
//    @Override
//    public void run() {
//        //锁成员对象
//        add2();
//    }

    //锁 类对象
    private static  synchronized void  add(){
        for (int i = 0; i < 1000000; i++)
            count++;
    }
    //锁类实例对象
    private   synchronized void  add2(){
        for (int i = 0; i < 1000000; i++)
            count++;
    }




}
