package com.thinking.my.designpatterns.templatemethod;

/**
 * @Author:liyong40
 * @Date:2019/12/30
 */
public class Test {

    public static void main(String[] args){

//炒 - 手撕包菜
        ConcreteClass_BaoCai BaoCai = new ConcreteClass_BaoCai();
        BaoCai.cookProcess();

//炒 - 蒜蓉菜心
        ConcreteClass_CaiXin caiXin = new ConcreteClass_CaiXin();
        caiXin.cookProcess();
    }

}
