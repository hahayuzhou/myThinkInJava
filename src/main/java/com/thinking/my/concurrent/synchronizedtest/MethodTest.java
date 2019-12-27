package com.thinking.my.concurrent.synchronizedtest;

/**
 * @Author:liyong40
 * @Date:2019/12/25
 */
public class MethodTest {

    private static int a;


    public  synchronized void addNum(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a++;
//        System.out.println(a);
    }
    public synchronized void addNum2(){
        a++;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static synchronized void addNum3(){
        a++;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void addNum4(){
        synchronized (this){
            a++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void addNum5(){
        synchronized (MethodTest.class){
            a++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
//        Runnable run1 = () ->{
//            new MethodTest().addNum();
//        };
        for(int i = 0;i<1000;i++){
            new Thread(() ->{
                addNum3();
            }).start();
            new Thread(() ->{
                addNum3();
            }).start();

        }
//        for(int i = 0;i<1000;i++){
//            new Thread(() ->{
//                new MethodTest().addNum();
//            }).start();
//            new Thread(() ->{
//                new MethodTest().addNum();
//            }).start();
//
//        }
        System.out.println(a);


        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a);

    }
}
