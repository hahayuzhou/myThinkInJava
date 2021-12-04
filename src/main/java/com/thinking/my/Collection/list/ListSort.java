package com.thinking.my.Collection.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liyong
 * @Date 2021/6/25 5:41 下午
 **/
public class ListSort {


    public static void main(String[] args) {
        List<List<String>> tlist = new ArrayList<>();
        for(int i=0;i<10;i++){
            List<String> list = new ArrayList<>();
            for(int j=0;j<5;j++){
                list.add(j+i+"");
            }
            tlist.add(list);
        }
        System.out.println(tlist);

//        tlist = tlist.stream().sorted(Comparator.comparing(e->e.get(3))).collect(Collectors.toList());
//        System.out.println(tlist);
        tlist = tlist.stream().sorted(Comparator.comparing((List<String> l)-> l.get(2)).reversed()).collect(Collectors.toList());
        System.out.println(tlist);

    }
}
