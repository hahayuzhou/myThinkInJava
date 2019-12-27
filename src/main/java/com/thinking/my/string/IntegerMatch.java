package com.thinking.my.string;

/**
 * Created by liyong on 2017/2/10.
 */
public class IntegerMatch {
    public static void main(String[] args) {
        System.out.println("-1234".matches("-?\\d+"));
        System.out.println("5678".matches("-?\\d+"));
    }
}
