package com.thinking.my.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liyong
 * @Date 2020/7/13 7:24 下午
 **/
public class StreamTest {

    public static void main(String[] args) {
        List<Integer> primes =  new ArrayList();
        for(int i=0;i<10;i++){

            primes.add(i);

        }

        List ll =  primes.stream().filter(i->i!=5).collect(Collectors.toList());
        System.out.println(ll);
    }



}
