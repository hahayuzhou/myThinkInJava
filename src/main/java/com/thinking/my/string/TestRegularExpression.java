package com.thinking.my.string;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liyong on 2017/2/11.
 */
public class TestRegularExpression {

    public static void main(String[] args) {
        Random r = new Random(47);
        System.out.println(r.nextInt(10));
        System.out.println(r.nextInt(100));
        System.out.println(r.nextInt(1000));
        System.out.println(r.nextInt(1000));
        System.out.println(r.nextInt(1000));
        System.out.println(r.nextInt(1000));
        System.out.println(r.nextInt(1000));
        System.out.println(Math.random()*10);
        System.out.println(Math.random()*10);
        System.out.println(Math.random());
        System.out.println(Math.random());
        ConcurrentHashMap concurrentHashMap;
        HashMap hashMap;
        CopyOnWriteArrayList copyOnWriteArrayList;
        if(args.length<2){
            System.out.print("Usage:\njava TestRegularExpression "+
            "characterSequence regularExpression+");
            System.exit(0);
        }
        System.out.println("Input: \"" +args[0] +"\"");
        for(String arg:args){
            System.out.println("Regular expression: \"" + arg + "\"");
            Pattern p = Pattern.compile(arg);
            Matcher m = p.matcher(args[0]);
            while(m.find()){
                System.out.println("Match \"" +m.group() +"\" at positions " +
                m.start() + "-" + (m.end() -1 ));

            }
        }
    }
}
