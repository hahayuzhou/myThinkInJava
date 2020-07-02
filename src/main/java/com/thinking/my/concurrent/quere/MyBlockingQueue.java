package com.thinking.my.concurrent.quere;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 自己实现阻塞队列
 * @Author liyong
 * @Date 2020/5/14 3:55 下午
 **/
public class MyBlockingQueue {

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    //存数据
    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length){
                notFull.await();//存满了阻塞
            }
            items[putptr] = x;
            if (++putptr == items.length){
                putptr = 0;
            }
            ++count;
            notEmpty.signal();//存好唤醒取线程
        } finally {
            lock.unlock();
        }
    }

    //取数据
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();//取完了阻塞
            Object x = items[takeptr];
            if (++takeptr == items.length)
                takeptr = 0;
            --count;
            notFull.signal();// 取走唤醒存线程
            return x;
        } finally {
            lock.unlock();
        }
    }

}
