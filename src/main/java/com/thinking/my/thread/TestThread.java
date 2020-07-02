package com.thinking.my.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by liyong on 2019/1/24.
 */
public class TestThread {

    static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue(10);
    static ExecutorService executor1 =
            new ThreadPoolExecutor(
                    2,
                    10,
                    1,
                    TimeUnit.SECONDS,
                    queue
            );

    public static void main(String[] args) {

         LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        List<String> list = new ArrayList<>();
        for(int i=0;i<100;i++){
//            list.add(i+"");
            queue.offer(i+"");
        }
//        MyThread2 myThread2 = new MyThread2();
//        myThread2.add(list);
////        myThread2.run();
//        MyThread2 myThread3 = new MyThread2();
//        myThread3.add(list);
//        myThread3.add(list);
//        myThread3.run();

//        MyThread3 myThread2 = new MyThread3();
//        myThread2.add(queue);
//        MyThread3 myThread3 = new MyThread3();
//        myThread3.add(queue);
//        executor1.execute(myThread2);
//        executor1.execute(myThread3);
        MyThread4 myThread2 = new MyThread4();
        myThread2.add(queue);
        MyThread4 myThread3 = new MyThread4();
        myThread3.add(queue);
        executor1.execute(myThread2);
        executor1.execute(myThread3);

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
class MyThread2 implements Runnable{

    private List<List<String>> lists = new ArrayList<>();

   public void add(List<String> list){
       lists.add(list);
   }



    @Override
    public void run() {
       try{
           lists.forEach(e->{

               e.forEach(o->{
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException ex) {
                       ex.printStackTrace();
                   }
                   System.out.println(o);
                   if(o.equals("5")){
                       int a  = Integer.parseInt("a");
                   }
               });
           });
       }catch (Exception e){
           e.printStackTrace();
       }


    }
}
class MyThread3 implements Runnable{

    private List<LinkedBlockingQueue<String>> lists = new ArrayList<>();

   public void add(LinkedBlockingQueue<String> queue){
       lists.add(queue);
   }
    @Override
    public void run() {
       try{
           lists.forEach(e->{
               String s;
               while((s= e.poll())!=null){
                   System.out.println(Thread.currentThread()+":"+s);
               }
//               for(int i=0;i<e.size();i++){
////                   try {
////                       Thread.sleep(100);
////                   } catch (InterruptedException ex) {
////                       ex.printStackTrace();
////                   }
////                   System.out.println(Thread.currentThread()+":"+e.peek());
//                   System.out.println(Thread.currentThread()+":"+e.poll());
//               }

           });
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
class MyThread4 implements Runnable{

    private LinkedBlockingQueue<String> queue;

   public void add(LinkedBlockingQueue<String> queue){
       this.queue = queue;
   }
    @Override
    public void run() {
       try{
           while (true){
               String s;
               while((s= queue.poll())!=null){
                   System.out.println(Thread.currentThread()+":"+s);
               }
               Thread.sleep(1000L);
//               for(int i=0;i<e.size();i++){
////                   try {
////                       Thread.sleep(100);
////                   } catch (InterruptedException ex) {
////                       ex.printStackTrace();
////                   }
////                   System.out.println(Thread.currentThread()+":"+e.peek());
//                   System.out.println(Thread.currentThread()+":"+e.poll());
//               }

           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
