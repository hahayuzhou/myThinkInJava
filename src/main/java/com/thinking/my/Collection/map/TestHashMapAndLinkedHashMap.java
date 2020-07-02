package com.thinking.my.Collection.map;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author liyong
 * @Date 2020/6/8 2:03 下午
 **/
public class TestHashMapAndLinkedHashMap {


    public static void main(String[] args) {
        int size = 10000000;
        long start;
//        Map<String,String> linkedHashMap = new LinkedHashMap<>();
//        start =  System.currentTimeMillis();
//        for(int i=0;i<size;i++){
//            linkedHashMap.put(i+"",i+"");
//        }
//        System.out.println("linkedHashMap"+(System.currentTimeMillis()-start));

//        start =  System.currentTimeMillis();
//        linkedHashMap.entrySet().forEach(e->{
//        });
//        System.out.println("linkedHashMapIt:"+(System.currentTimeMillis()-start));
        Map<String,String> hashMap = new HashMap<>();
        start =  System.currentTimeMillis();
        for(int i=0;i<size;i++){
            hashMap.put(i+"",i+"");
        }
        System.out.println("hashMap:"+(System.currentTimeMillis()-start));
//         hashMap = new HashMap<>();
//       start =  System.currentTimeMillis();
//        for(int i=0;i<size;i++){
//            hashMap.put(i+"",i+"");
//        }
//        System.out.println("hashMap2:"+(System.currentTimeMillis()-start));
//        start =  System.currentTimeMillis();
//        hashMap.entrySet().forEach(e->{
//
//        });
//        System.out.println("hashMapIt:"+(System.currentTimeMillis()-start));
//
//
//         linkedHashMap = new LinkedHashMap<>();
//        start =  System.currentTimeMillis();
//        for(int i=0;i<size;i++){
//            linkedHashMap.put(i+"",i+"");
//        }
//        System.out.println("linkedHashMap"+(System.currentTimeMillis()-start));
//
//        start =  System.currentTimeMillis();
//        linkedHashMap.entrySet().forEach(e->{
//        });
//        System.out.println("linkedHashMapIt:"+(System.currentTimeMillis()-start));
        while (true){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
