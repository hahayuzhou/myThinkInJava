package com.thinking.my.Collection.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:liyong40
 * @Date:2020/4/24
 */
public class MyMap {

    public static void main(String[] args) {


        int expetedSize = 10000000;
        int initialCapacity =  (int) ((float) expetedSize / 0.75F + 1.0F);
        Map<String,String> map = new HashMap<>(initialCapacity);
        System.out.println(map.size());
        System.out.println(map.size());

        int highestOneBit = Integer.highestOneBit(expetedSize);
        int lowestOneBit = Integer.lowestOneBit(expetedSize);
        System.out.println(highestOneBit);
        System.out.println(lowestOneBit);

        System.out.println(1 << 3);
        System.out.println(14 >> 3);
        System.out.println(14 / 4);
        System.out.println(14 >>2);

        map.put("a","b");
        System.out.println();
        List<String> list = new ArrayList<>(map.values());
        System.out.println(list);
    }


}
