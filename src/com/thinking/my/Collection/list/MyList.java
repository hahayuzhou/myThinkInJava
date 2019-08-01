package com.thinking.my.Collection.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liyong on 2019/3/14.
 */
public class MyList {

    public static void main(String[] args) {
        List s  = new ArrayList();
        s.add("a");
        s.add("b");
        s.add("d");
        List<String> b = new ArrayList<>();
        b.add("a");
        b.add("c");
        b.add("d");
//        System.out.println(diff(s,b));
//        s.removeAll(b);//差集
//        s.retainAll(b);//交集
        s.addAll(b);//并集
        System.out.println(s);


    }

    /**
     * 求ls对ls2的差集,即ls中有，但ls2中没有的
     *
     * @param ls
     * @param ls2
     * @return
     */
    public static List diff(List ls, List ls2) {
        List list = new ArrayList(ls.size());
        list.addAll(ls);
        list.removeAll(ls2);

        List list1 = new LinkedList();
        return list;
    }
}
