package com.thinking.my.algorithm.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2021/1/20 9:46 下午
 **/
public class HashMapSort {

    public static void main(String[] args) {
        Map<String,Long> map = new HashMap<>();

        map.put("a1",2L);
        map.put("aa",3L);
        map.put("ab",4L);
        map.put("ac",5L);

        List<Map.Entry<String,Long>> list = new ArrayList<>(map.entrySet());
        list.sort((a,b)->b.getValue().compareTo(a.getValue()));
//        list.forEach(e-> System.out.println(e.getKey()));
        list.stream().limit(2).forEach(e-> System.out.println(e.getKey()));
    }
}
