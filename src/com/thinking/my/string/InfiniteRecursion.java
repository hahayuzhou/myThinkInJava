package com.thinking.my.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyong on 2017/2/10.
 */
public class InfiniteRecursion {

    public String toString() {
        return " InfiniteRecursion address: " + super.toString() + "\n";
    }

    public static void main(String[] args) {
        List<InfiniteRecursion> v = new ArrayList<InfiniteRecursion>();
        for(int i = 0;i<10;i++)
            v.add(new InfiniteRecursion());
        System.out.println(v);
    }
}
