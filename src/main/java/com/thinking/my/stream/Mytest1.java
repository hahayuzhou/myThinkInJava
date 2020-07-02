package com.thinking.my.stream;

import java.sql.SQLOutput;
import java.util.stream.Stream;

/**
 * @Description
 * @Author liyong
 * @Date 2020/5/25 8:14 下午
 **/
public class Mytest1 {

    public static void main(String[] args) {
        Stream.iterate(new int[]{0,1},t->new int[]{t[1],t[0]+t[1]})
                .limit(20)
                .forEach(t-> System.out.println("("+t[0]+","+t[1]+")"));
    }
}
