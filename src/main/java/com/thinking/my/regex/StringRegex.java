package com.thinking.my.regex;

/**
 * String正则表达
 */
public class StringRegex {
    public static void main(String[] args) {
        String aa = "201908083682716";
        String[] arr = aa.split("\001");
        String aaa = "201908198882716";
        aaa = "201908083682716";
        String[] arr1 = aaa.split("\001");
        System.out.println(arr.toString());
    }
}
