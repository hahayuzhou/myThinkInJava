package com.thinking.my.Collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liyong on 2017/2/6.
 */
public class SimpleCollection {
    public static void main(String[] args) {
        Collection<Integer> c = new ArrayList<>();
        for(int i = 0;i<10;i++){
            c.add(i);
        }
        for(Integer i :c){
            System.out.println(i + ", ");
        }

        ArrayList l = new ArrayList<>();
        l.indexOf(1);
        HashMap m  = new HashMap();
        m.hashCode();
    }
}
