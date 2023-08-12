package com.thinking.my.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author liyong
 * @Date 2020/11/12 8:42 下午
 **/
public class Test {

    private static final String TABLE_NAME_REGX = "[\\s|\\n]+(from|FROM|join|JOIN)[\\s|\\n]+[a-z|A-Z|_|-|0-9]*[\\s|\\n]+";

    /**
     *   // * 零次或多次匹配前面的字符或子表达式。例如，zo* 匹配"z"和"zoo"。* 等效于 {0,}。
     *  // ? 零次或一次匹配前面的字符或子表达式。例如，"do(es)?"匹配"do"或"does"中的"do"。? 等效于 {0,1}。
     *  //  + 一次或多次匹配前面的字符或子表达式。例如，"zo+"与"zo"和"zoo"匹配，但与"z"不匹配。+ 等效于 {1,}。
     * @param args
     */
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("F*G?(T|S*L?)?");
        String input = "FGSLFGTSSTGFFGFSFLTFFGT";
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }


    }


}
