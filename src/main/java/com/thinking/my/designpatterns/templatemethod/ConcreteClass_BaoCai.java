package com.thinking.my.designpatterns.templatemethod;

/**
 * @Author:liyong40
 * @Date:2019/12/30
 */
//炒手撕包菜的类
public class ConcreteClass_BaoCai extends  AbstractClass{
    @Override
    public void  pourVegetable(){
        System.out.println("下锅的蔬菜是包菜");
    }
    @Override
    public void  pourSauce(){
        System.out.println("下锅的酱料是辣椒");
    }
}
//炒蒜蓉菜心的类
class ConcreteClass_CaiXin extends  AbstractClass{
    @Override
    public void  pourVegetable(){
        System.out.println("下锅的蔬菜是菜心");
    }
    @Override
    public void  pourSauce(){
        System.out.println("下锅的酱料是蒜蓉");
    }
}


