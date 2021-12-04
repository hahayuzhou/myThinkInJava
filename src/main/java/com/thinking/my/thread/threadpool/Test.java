package com.thinking.my.thread.threadpool;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

/**
 * @Description
 * @Author liyong
 * @Date 2021/4/16 11:42 上午
 **/
public class Test {

    private static int ctlOf(int rs, int wc) { return rs | wc; }

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;

    public static void main(String[] args) {
        int a = ctlOf(2,3);
        System.out.println(a);
        System.out.println(RUNNING);
        System.out.println(-1<<1);
        System.out.println(1<<1);
        System.out.println(-1<<2);
        System.out.println(1<<2);
        System.out.println((-1)<<29);
        System.out.println(1<<29);
        System.out.println(1*Math.pow(10,5));
        System.out.println((-4)|3);
        System.out.println(4&3);
        System.out.println(Integer.toBinaryString(-4));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(~1));
        System.out.println(Integer.toBinaryString(1));

//        System.out.println(Integer.toBinaryString(-100));
//        System.out.println(Integer.toBinaryString(100));
//        System.out.println(Integer.toBinaryString(-RUNNING));
//        System.out.println(Integer.toBinaryString(RUNNING));

    }
}
