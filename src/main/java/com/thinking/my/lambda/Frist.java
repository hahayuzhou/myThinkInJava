package com.thinking.my.lambda;

import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;

/**
 * Created by liyong on 2017/3/3.
 */
public class Frist {

    Runnable noArguments = () -> System.out.println("hello world");
    ActionListener oneArguments = event -> System.out.println("hello world");

    BinaryOperator<Integer> add = (x, y) -> x + y;


    public static void main(String[] args) {
        Frist frist = new Frist();
        frist.noArguments.run();
        int aa = frist.add.apply(1, 3);

        System.out.println(aa);

        final String[] array = {"world", "hello"};

        System.out.println(array[0].toString());
    }
}


