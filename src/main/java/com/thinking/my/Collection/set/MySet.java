package com.thinking.my.Collection.set;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liyong on 2019/3/14.
 */
public class MySet {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
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
              b =   set.retainAll(set2);//交集
////        set.addAll(set2);//并集
//        set.removeAll(set2);//差集
        System.out.println(set);
        System.out.println(b);
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
