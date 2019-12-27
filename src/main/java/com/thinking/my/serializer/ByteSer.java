package com.thinking.my.serializer;

/**
 * Created by liyong on 2019/4/22.
 */
public class ByteSer {


    public static byte[] GetBytesFromInt32(int value) {
        byte[] buffer = new byte[4];
        for (int i = 0; i < 4; i++) {
            System.out.println("i:"+i+"--"+(value >> (8 * i)));
            buffer[i] = (byte) (value >> (8 * i));
            System.out.println(buffer[i]);
        }
        return buffer;
    }

    public static void main(String[] args) {
        System.out.println(GetBytesFromInt32(4).toString());
        System.out.println(GetBytesFromInt32(999999999).toString());
        byte[] bb = GetBytesFromInt32(123456789);
        for(byte b:bb){
            System.out.println(b);
        }
//        int value = 999999999;
        int value = 123456789;
        int a = value >>0;
        int b = value >>8;
        int c = value >>16;
        int d = value >>32;
        int e = value >>64;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(a<<0);
        System.out.println(b<<8);
        System.out.println(c<<16);
        System.out.println(d<<32);
        System.out.println(e<<64);
    }
}
