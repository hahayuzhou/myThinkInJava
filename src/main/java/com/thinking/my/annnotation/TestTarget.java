package com.thinking.my.annnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * Created by liyong on 2019/2/15.
 */


//[rɪˈtenʃn]保留
@Retention(RetentionPolicy.RUNTIME)
// [ˈtɑ:gɪt]目标
@Target(ElementType.METHOD)
@interface Test {

}

class Junit{
    @Test
    public static void test1(){

    }

    public static void test2(){

    }

    public static void test3(){

    }

    @Test
    public static void test4(){

    }

}

public class TestTarget{
    public static void main(String[] args) throws Exception{
        //1.1通过反射获取类
        Class<?> forName = Class.forName("com.thinking.my.annnotation.Junit");
        //1.2获取该类自身声明的所有方法
        Method[] methods = forName.getDeclaredMethods();
        int checkCount = 0; //测试的数量
        int uncheckCount = 0;  //未测试的数量
        for (Method method : methods) {
            if(method.isAnnotationPresent(Test.class)){
                checkCount++;
            }else{
                uncheckCount++;
            }
        }
        System.out.println("测试的方法有" + checkCount);
        System.out.println("未测试的方法有" + uncheckCount);
    }
}