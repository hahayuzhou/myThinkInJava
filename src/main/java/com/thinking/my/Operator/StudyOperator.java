package com.thinking.my.Operator;

public class StudyOperator {

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        // | 如果相对应位都是0，则结果为0，否则为1
        int c = a | b;

        System.out.println(a+"-"+b+"-"+c);// 1-2-3

        a = 2;

        c = a | b;

        System.out.println(a+"-"+b+"-"+c);// 2-2-2
        a = 1;
        b = 1;

        c = a | b;
        System.out.println(a+"-"+b+"-"+c);// 1-1-1

    }
}
