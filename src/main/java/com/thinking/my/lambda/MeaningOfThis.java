package com.thinking.my.lambda;

/**
 * @Description 下面的代码执行时会有什么样的输出呢，4、5、6还是42?
 * @Author liyong
 * @Date 2020/4/30 8:39 下午
 **/
public class MeaningOfThis {
    public final int value = 4;
    public void doIt()
    {
        int value = 6;
        Runnable r = new Runnable(){
            public final int value = 5;
            public void run(){
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }
    public static void main(String...args)
    {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt();
        //答案是5，因为this指的是包含它的Runnable，而不是外面的类MeaningOfThis。
    }
}
