package com.thinking.my.algorithm;

/**
 * @Description
 * @Author liyong
 * @Date 2021/4/15 8:14 下午
 **/
public class IntegerTest {

    public static int parseInt(String s, int radix)
            throws NumberFormatException
    {
        /*
         * WARNING: This method may be invoked early during VM initialization
         * before IntegerCache is initialized. Care must be taken to not use
         * the valueOf method.
         */

        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " less than Character.MIN_RADIX");
        }

        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " greater than Character.MAX_RADIX");
        }

        int result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+') {
                    throw new RuntimeException();
                }

                if (len == 1) // Cannot have lone "+" or "-"
                    throw new RuntimeException();
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++),radix);
                if (digit < 0) {
                    throw new RuntimeException();
                }
                if (result < multmin) {
                    throw new RuntimeException();
                }
                result *= radix;
                if (result < limit + digit) {
                    throw new RuntimeException();
                }
                result -= digit;
            }
        } else {
            throw new RuntimeException();
        }
        return negative ? result : -result;
    }


    public static void main(String[] args) {
        int a = parseInt("-12323345",10);
        System.out.println(a);
    }
}
