package com.thinking.my.algorithm.sum;

import javax.swing.*;

/**
 * @Description
 * @Author liyong
 * @Date 2020/6/9 10:55 上午
 **/
public class Test {

    public static void main(String[] args) {
        cA:
        for(int i = 0;i<10;i++){
            cb:
            for(int j=0;j<10;j++){
                while (j<i){
                    j++;
                    if(j==5){
                        continue cA;
//                        break;
                    }
                    System.out.println(j+":"+i);
                }
            }
            System.out.println(i);
        }
    }
}
