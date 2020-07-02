package com.thinking.my.thread.threadpool;

import java.sql.SQLOutput;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Description
 * @Author liyong
 * @Date 2020/5/14 3:10 下午
 **/
public class TestThreadPool {
    static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue(10);
    static LinkedBlockingQueue<Runnable> queue2 = new LinkedBlockingQueue(10);
   static ExecutorService executor1 =
            new ThreadPoolExecutor(
                    2,
                    10,
                    1,
                    TimeUnit.SECONDS,
                    queue
            );
   static ExecutorService executor2 =
            new ThreadPoolExecutor(
                    2,
                    2,
                    1,
                    TimeUnit.SECONDS,
                    queue2
            );
   static ExecutorService loadAuthPoolExecutor2 =
            new ThreadPoolExecutor(
                    2,
                    10,
                    1,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>()//一个基于链表结构的阻塞队列，此队列按FIFO排序元素，吞吐量通常要高于ArrayBlock-ingQueue。静态工厂方法Executors.newFixedThread-Pool()使用了这个队列
            );
   static ExecutorService threadPoolExecutor =
            new ThreadPoolExecutor(
                    2,
                    10,
                    1,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<>()
            );
   static ExecutorService threadPoolExecutor2 =
            new ThreadPoolExecutor(
                    2,
                    10,
                    1,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<>()
            );
   static ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            System.out.println(Thread.currentThread());
            return new Thread(r);
        }
    };
    /**
     * AbortPolicy：直接抛出异常。
     * CallerRunsPolicy：只用调用者所在线程来运行任务。
     * DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。
     * DiscardPolicy：不处理，丢弃掉。
     */
    static RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r.toString());
        }
    };
   static ExecutorService executor =
            new ThreadPoolExecutor(
                    2,
                    5,
                    1,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<>(),//一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于Linked-Block-ingQueue，静态工厂方法Executors.newCachedThread-Pool使用了这个队列。
                    threadFactory,
                    handler
            );
   static ExecutorService executor4 =
            new ThreadPoolExecutor(
                    2,
                    5,
                    1,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(1),//是一个基于数组结构的有界阻塞队列，此队列按FIFO（先进先出）原则对元素进行排序
                    threadFactory,
                    handler
            );


    public static void main(String[] args) {
        for(int i=0;i<11;i++)
//            loadAuthPoolExecutor.execute(runnable);
        {
            int finalI = i;
            executor1.execute(()->{
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ss"+ finalI);
            });
        }
        for(int i=11;i<21;i++)
//            loadAuthPoolExecutor.execute(runnable);
        {
            int finalI = i;
            executor2.execute(()->{
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ss2:"+ finalI);
            });
        }
    }

   static Runnable runnable = ()->{

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ss");
    };

}
