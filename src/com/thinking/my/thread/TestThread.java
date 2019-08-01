package com.thinking.my.thread;

/**
 * Created by liyong on 2019/1/24.
 */
public class TestThread {

    public static void main(String[] args) {
        Runnable runnable = new MyThread("run");
        Thread a = new Thread(new MyThread("a"));
        a.start();
        Thread b = new Thread(new MyThread("b"));
        b.start();
        Thread c = new Thread(new MyThread("c"));
        c.start();
        Thread a1 = new Thread(runnable);
        a1.start();
        Thread b1 = new Thread(runnable);
        b1.start();
        Thread c1 = new Thread(runnable);
        c1.start();

    }
}


class MyThread implements Runnable{

    private int a = 5;

    private String str;
    MyThread(String str){
        this.str = str;
    }

    @Override
    public void run() {
        System.out.println(str+":"+(--a));

    }
}
