package com.thinking.my.javaCompiler;

/**
 * @Description
 * @Author liyong
 * @Date 2021/8/20 4:07 下午
 **/
public class DefaultHelloService  implements HelloService{
    @Override
    public void sayHello(String name) {
        System.out.println("name:"+name);
    }
}
