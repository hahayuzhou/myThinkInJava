package com.thinking.my.javaCompiler.Javassist;

import com.thinking.my.javaCompiler.HelloService;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @Description
 * @Author liyong
 * @Date 2021/8/20 4:01 下午
 **/
public class JavassistClient {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.thinking.my.javaCompiler.DefaultHelloService");
        CtMethod ctMethod = cc.getDeclaredMethod("sayHello", new CtClass[]{pool.get("java.lang.String")});
        ctMethod.insertBefore("System.out.println(\"insert before by Javassist\");");
        ctMethod.insertAfter("System.out.println(\"insert after by Javassist\");");
        Class<?> klass = cc.toClass();
        System.out.println(klass.getName());
        HelloService helloService = (HelloService) klass.getDeclaredConstructor().newInstance();
        helloService.sayHello("throwable");
    }
}
