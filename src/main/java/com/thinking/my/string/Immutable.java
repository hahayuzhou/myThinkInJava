package com.thinking.my.string;

import java.util.UUID;

/**
 * Created by liyong on 2017/2/10.
 */
public class Immutable {

    public static String upcase(String s){
        return s.toUpperCase();
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);

    }
}
