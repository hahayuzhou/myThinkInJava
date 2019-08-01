package com.thinking.my.Collection.set;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by liyong on 2019/3/14.
 */
public class MySet {

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        HashSet<String> set2 = new HashSet<>();
        set2.add("a");
        set2.add("c");

//        set.retainAll(set2);//交集
//        set.addAll(set2);//并集
        set.removeAll(set2);//差集
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

        float f = 0.6332f;

        double d = 0.6332;
    }
}
