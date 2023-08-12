package com.thinking.my.extend;

public class test {

    public static void main(String[] args) {
        Apple apple = new Apple();
        apple.setName("apple");
        apple.setColour("red");
        System.out.println(apple);
        BaseClass apple1 = new Apple();
        apple1.setName("apple");
        ((Apple)apple1).setColour("blue");

        System.out.println(apple1);
    }
}
