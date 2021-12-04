package com.thinking.my.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description
 * @Author liyong
 * @Date 2021/6/17 3:13 下午
 **/
public class TestCountDownLatch {


    private String aaa = "ssss";


    public static void main(String[] args) {
        //创建2个点的CountDownLatch对象
        CountDownLatch countDownLatch = new CountDownLatch(2);

        //将countDownLatch对象的引用传递给子线程里
        WorkThread workThread1 = new WorkThread(countDownLatch);
        WorkThread workThread2 = new WorkThread(countDownLatch);
        workThread1.start();
        workThread2.start();

        try {
            //调用await方法阻塞当前线程，等待子线程完成后在继续执行
            countDownLatch.await(1000,TimeUnit.MILLISECONDS);
            long ccc =  countDownLatch.getCount();
            System.out.println(ccc);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("run next process.");

//        ExecutorService executor = Executors.newCachedThreadPool();
//        //创建2个点的CountDownLatch对象
//        CountDownLatch countDownLatch = new CountDownLatch(2);
//        Task task = new Task("one", 5,countDownLatch);
//        Task task2 = new Task("two", 2,countDownLatch);
//        Future<?> future = executor.submit(task);
//        Future<?> future2 = executor.submit(task2);
//        List<Future<?>> futures = new ArrayList<Future<?>>();
//        futures.add(future);
//        futures.add(future2);
//        try {
//            if (executor.awaitTermination(3, TimeUnit.SECONDS)) {
//                System.out.println("task finished");
//            } else {
//                System.out.println("task time out,will terminate");
//                for (Future<?> f : futures) {
//                    if (!f.isDone()) {
//                        f.cancel(true);
//                    }
//                }
//            }
//        } catch (InterruptedException e) {
//            System.out.println("executor is interrupted");
//        } finally {
//            executor.shutdown();
//        }
    }

    static class Task implements Runnable {
        public String name;
        private int time;
        private CountDownLatch countDownLatch;

        public Task(String s, int t,CountDownLatch countDownLatch) {
            name = s;
            time = t;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            for (int i = 0; i < time; ++i) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(name
                            + " is interrupted when calculating, will stop...");
                    return; // 注意这里如果不return的话，线程还会继续执行，所以任务超时后在这里处理结果然后返回
                }

                System.out.println("task " + name + " " + (i + 1) + " round");
            }
            System.out.println("task " + name + " finished successfully");
            countDownLatch.countDown();
        }
    }
}


class WorkThread extends Thread {
    private CountDownLatch countDownLatch;

    public WorkThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(getName() + "run start.");
            sleep(1000);
            //执行子任务完毕之后，countDown减少一个点
            System.out.println(getName() + "run finished.");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


