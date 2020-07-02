package com.thinking.my.Collection.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:liyong40
 * @Date:2020/4/24
 */
public class MyMap {

    public static void main(String[] args) {


        int expetedSize = 0;
        int initialCapacity =  (int) ((float) expetedSize / 0.75F + 1.0F);
        Map<String,String> map = new HashMap<>(initialCapacity);
        System.out.println(map.size());
        System.out.println(map.size());
    }


}
