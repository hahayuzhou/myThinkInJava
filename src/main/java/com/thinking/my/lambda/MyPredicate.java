package com.thinking.my.lambda;

/**
 * 接口只有一个抽象方法的 他就是 函数式接口
 * @param <T>
 */
@FunctionalInterface
public interface MyPredicate<T> {

    boolean test(T t);

    default int add(int a,int b){
        return a+b;
    }
}
