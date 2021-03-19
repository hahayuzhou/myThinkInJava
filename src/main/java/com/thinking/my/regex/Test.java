package com.thinking.my.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author liyong
 * @Date 2020/11/12 8:42 下午
 **/
public class Test {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("F*G?(T|S*L?)?");
        String input = "FGSLFGTSSTGFFGFSFLTFFGT";
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        int a = 3;
        int b = a;
        System.out.println(a+"--"+b);
        a = 5;
        System.out.println(a+"--"+b);

    }
}
