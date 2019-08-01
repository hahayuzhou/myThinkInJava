package com.thinking.my.annnotation;

/**
 * Created by liyong on 2019/2/15.
 */
public class Apple {

    @FruitName("apple")
    String appleName;

    public void displayAppleName()
    {
        System.out.println(appleName);
    }

    public static void main(String[] args) {
        Apple apple = new Apple();
        apple.displayAppleName();
    }
}
