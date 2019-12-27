package com.thinking.my.concurrent.volatiletest;

/**
 * @Author:liyong40
 * @Date:2019/12/27
 */
public class VolatileExample {

    private int a = 0;

    private volatile  boolean flag = false;


    public void writer (){
        a = 1;
        flag = true;
    }

    public void reader (){
        while (true){
            if(flag){
                int i = a;
                System.out.println(i);
                return;
            }
        }

    }


    public static void main(String[] args) {
       VolatileExample cc =  new VolatileExample();

        new Thread(()->{
                cc.reader();
        }).start();


        new Thread(()->{
            synchronized (VolatileExample.class){
                cc.writer();
            }
        }).start();
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
