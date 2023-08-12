package com.thinking.my.Collection.list;

import java.util.*;

/**
 * Created by liyong on 2019/3/14.
 */
public class MyList {

    public static void main(String[] args) {

        List<String> aa = new ArrayList<>();
        aa.add("a");
        aa.add("c");
        aa.add("c");
        List<String> bb = new ArrayList<>();
        bb.add("a");
        bb.add("c");
        bb.add("d");
        String[] arr = aa.toArray(new String[0]);
        System.out.println(arr.length);
        System.out.println(arr[1]);

        aa.addAll(bb);
        String[] arr2 = aa.toArray(new String[0]);
        System.out.println(arr2.length);
        System.out.println(arr2[1]);



        for(int j=0;j<20;j++){
            List<List<String>> apples = new ArrayList<>();
            for(int i = 0;i<1000;i++){
                List a = new ArrayList();
                a.add("key"+i+j);
                a.add("vaasdfasdfasdfasdfasdfasfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasderwersdfszxcvzxcvzxcvz"+i+j);
                apples.add(a);
            }

            long start = System.currentTimeMillis();
            for(int m = 0;m<100;m++){
                Map<String,List<String>> map = new HashMap<>();
                String key;
                for (List<String> list:apples){
                    key = list.get(0);
//                list.remove(0);
                    map.put(key,list);
                }
            }

            String a = new String("a");
            System.out.println(System.currentTimeMillis()-start);
        }

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
