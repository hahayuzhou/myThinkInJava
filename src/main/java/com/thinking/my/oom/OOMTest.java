package com.thinking.my.oom;

/**
 * @Description
 * @Author liyong
 * @Date 2022/1/19 8:16 下午
 **/
public class OOMTest {
    public static void main(String[] args) {
        String str = "abcdefg12345";
        for(int i=0;i<100;i++){
            str +=str;
        }
        System.out.println(str);
    }
}
