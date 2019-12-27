package com.thinking.my.lambda;

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

    }
}
