package com.thinking.my.concurrent.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liyong on 2019/5/24.
 */
public class StudyHashmap {

    private HashMap hashMap = new HashMap();
    private HashMap hashMap2 = new HashMap(20);




    public static void main(String[] args) {
        System.out.println(1 << 30);

        Map map = new HashMap<>();

        map.put("name","liyong");
        Map cMap = new ConcurrentHashMap();
        cMap.put("name","liyong");
    }
}


