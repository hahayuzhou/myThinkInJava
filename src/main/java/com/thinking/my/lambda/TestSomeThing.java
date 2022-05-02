package com.thinking.my.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liyong on 2019/3/25.
 */
public class TestSomeThing {

    public static void main(String[] args) {
        IConvert<String, String> convert = Something::startsWith;
        String converted = convert.convert("123");
        System.out.println(convert);
        System.out.println(converted);

        // object methods
        Something something = new Something();
        IConvert<String, String> converter = something::endWith;
        converted = converter.convert("Java");
        System.out.println(converted);

        // constructor methods
        IConvert<String, Something> convert1 = Something::new;
        something = convert1.convert("constructors");


        List<Apple> list = new ArrayList<>();

        list.add(new Apple(true,"ss"));
        list.add(new Apple(true,"ss"));

        List<String> cc = list.stream().filter(e->!e.isDdd()).map(Apple::getName).collect(Collectors.toList());
        List dd = cc.stream().filter(e->e.equals("ss")).collect(Collectors.toList());
        System.out.println(cc);
        System.out.println(dd);

    }


}

class Apple {
    private boolean ddd;
    private String name;

    public Apple(boolean ddd, String name) {
        this.ddd = ddd;
        this.name = name;
    }

    public boolean isDdd() {
        return ddd;
    }

    public void setDdd(boolean ddd) {
        this.ddd = ddd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
