package com.thinking.my.Collection.queue;

import java.util.PriorityQueue;

/**
 * @Description PriorityQueue 优先级队列 取数的时候 进行排序  只保证 第一个 元素最小
 * @Author liyong
 * @Date 2020/5/8 7:02 下午
 **/
public class TestPriorityQueue {
    public static void main(String[] args) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        //入列
        q.offer(3);
        q.offer(1);
        q.offer(2);
        q.offer(5);
        q.offer(4);

       Object[] array =  q.toArray();
        for (Object o : array) {
            System.out.println(o);
            /**
             * 1
             * 3
             * 2
             * 5
             * 4
             */
        }

        //出列
        System.out.println(q.poll());  //1
        System.out.println(q.poll());  //2
        System.out.println(q.poll());  //3
        System.out.println(q.poll());  //4
        System.out.println(q.poll());  //5
    }
}
