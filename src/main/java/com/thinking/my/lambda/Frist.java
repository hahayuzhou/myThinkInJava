package com.thinking.my.lambda;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by liyong on 2017/3/3.
 */
public class Frist {

    Runnable noArguments = () -> System.out.println("hello world");
    ActionListener oneArguments = event -> System.out.println("hello world");

    BinaryOperator<Integer> add = (x, y) -> x + y;
    static BinaryOperator<Integer> add2 = (x, y) -> x + y;



    Comparator<Integer> byWeight = ( Integer a1,Integer a2) -> a1.compareTo(a2);

    public static <T,R> List<R> map(List<T> list,Function<T,R> f){
        List<R> rs = new ArrayList<>();
        list.forEach(e->{
            rs.add(f.apply(e));
        });
        return rs;
    }

    public static void main(String[] args) {
        Frist frist = new Frist();
        frist.noArguments.run();
        int aa = frist.add.apply(1, 3);
        System.out.println(aa);

        System.out.println(add2.apply(4,5));
        System.out.println((5 % 2 == 0)+"");

        final String[] array = {"world", "hello"};

        System.out.println(array[0].toString());
    }
}


