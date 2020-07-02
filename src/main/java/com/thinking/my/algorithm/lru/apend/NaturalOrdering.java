package com.thinking.my.algorithm.lru.apend;

import java.io.Serializable;

/**
 * @Description
 * @Author liyong
 * @Date 2020/5/19 5:43 下午
 **/
public class NaturalOrdering extends Ordering<Comparable> implements Serializable {
    static final NaturalOrdering INSTANCE = new NaturalOrdering();

    @Override
    public int compare(Comparable o1, Comparable o2) {
        return 0;
    }
}
