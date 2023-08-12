package com.thinking.my.Collection.set;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by liyong on 2019/3/14.
 */
public class MySet {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");


        System.out.println(set.toString());

        Optional<String> d= set.stream().filter(e->"a".equals(e)).findFirst();
        if (d.isPresent()){
            System.out.println(d.get());
        } else {
            System.out.println(d);
            System.out.println(d.orElseGet(null));
        }
       Set<String> ff = set.stream().filter(e->"f".equals(e)).collect(Collectors.toSet());
        System.out.println("ff:"+ff);

        Set<String> set2 = new HashSet<>();
        set2.add("a");
        set2.add("c");

        boolean b=   set.retainAll(set2);//交集
        System.out.println(b);
//        set.addAll(set2);//并集
//        set.removeAll(set2);//差集
        System.out.println(set);

        AbstractSet set1 = new AbstractSet() {
            @Override
            public Iterator iterator() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };


        set = getSet();
        set2 = getSet2();
        List<String> aaa = new ArrayList<>();
        aaa.add("a");
        aaa.add("f");
//              b =   set.retainAll(set2);//交集
////        set.addAll(set2);//并集
        aaa.removeAll(set);
        System.out.println(aaa);
        System.out.println(set);
        set.removeAll(aaa);//差集
        System.out.println(set);
//        System.out.println(b);

        Set<String> ccc = new HashSet<>();

        System.out.println(ccc.toString());
        
        ccc = Collections.EMPTY_SET;
        System.out.println(ccc.toString());

    }

    public static Set<String> getSet(){
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        return set;
    }
    public static Set<String> getSet2(){
        Set<String> set = new HashSet<>();
        set.add("d");
        set.add("c");
        return set;
    }



}
