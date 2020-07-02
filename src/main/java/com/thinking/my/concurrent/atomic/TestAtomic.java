package com.thinking.my.concurrent.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description
 * @Author liyong
 * @Date 2020/5/8 3:06 下午
 **/
public class TestAtomic {
    private static final AtomicLong size = new AtomicLong(0);

    public static void main(String[] args) {
        long l = size.get();
        System.out.println(l);
        l = size.addAndGet(8L);
        System.out.println(l);
        l = size.incrementAndGet();
        System.out.println(l);
        l = size.incrementAndGet();
        System.out.println(l);
    }
}
