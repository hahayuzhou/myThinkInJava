package com.thinking.my.asserttest;

/**
 * @Author:liyong40
 * @Date:2019/12/30
 */
public class test {

    public static void main(String[] args) {
        String name = "";
        assert 1==2:"error 1==2";
        assert name==null:"error 1==2";
        System.out.println(name);
    }
}
