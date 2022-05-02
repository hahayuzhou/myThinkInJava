package com.thinking.my.math;

import java.util.Random;

/**
 * @Author:liyong40
 * @Date:2020/4/24
 */
public class Test {

    private static Random random = new Random();

    public static void main(String[] args) {
        int a = (int) (10*0.5);
        System.out.println(a);
        for(int i=0;i<10;i++){
//            System.out.println(random.nextInt(5));
            int idx = random.nextInt(2)%2;
            System.out.println(idx);
        }

        long l = 1234567899876989844l;
        int c = (int) l;
        System.out.println(c);

    }



}
