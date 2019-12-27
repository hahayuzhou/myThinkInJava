package com.thinking.my.lambda;

/**
 * Created by liyong on 2019/3/25.
 */
@FunctionalInterface
public interface IConvert<F,T> {
    T convert(F from);
}
