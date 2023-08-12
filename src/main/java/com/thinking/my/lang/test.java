package com.thinking.my.lang;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/22 8:15 下午
 **/
public class test {

    public static void main(String[] args) {
//        Set<String> strings = new HashSet<>(2000000);
//        Set<Long> longs = new HashSet<>(10000000);
//        long start = System.currentTimeMillis();
//        for(int i=0;i<10000000;i++){
//            String uuid = getuuid();
////            strings.add(uuid);
////            longHashCode(uuid);
//            longs.add(longHashCode(uuid));
//        }
//        System.out.println("use:"+(System.currentTimeMillis()-start));
//        System.out.println(strings.size());
//        System.out.println(longs.size());
//        StringBuilder stringBuilder = new StringBuilder();
//        System.out.println(longHashCode(stringBuilder.toString()));
        Long l = new Long(2);

        String a = "aa";
        String b = new String("aa");
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(a==b);
        System.out.println(a.equals(b));
        Long c = 2L;
        Long d = 2L;

        System.out.println(c.hashCode());
        System.out.println(l.hashCode());
        System.out.println(l == c);
        l = new Long(3);
        System.out.println(d == c);
        System.out.println(l.equals(c));



    }

    public static String getuuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public static long longHashCode(String str) {
        long hash = 0;
        char[] value = new char[str.length()];
        str.getChars(0, str.length(), value, 0);
        if (hash == 0 && value.length > 0) {
            char val[] = value;
            for (int i = 0; i < value.length; i++) {
                hash = 31 * hash + val[i];
            }
        }
        return hash;
    }


}
