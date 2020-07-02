package com.thinking.my.algorithm.lru.apend;

import java.util.Comparator;

/**
 * @Description
 * @Author liyong
 * @Date 2020/5/19 5:40 下午
 **/
public abstract class Ordering<T> implements Comparator<T> {
    public static <C extends Comparable> Ordering<C> natural() {
        return (Ordering<C>) NaturalOrdering.INSTANCE;
    }
}
