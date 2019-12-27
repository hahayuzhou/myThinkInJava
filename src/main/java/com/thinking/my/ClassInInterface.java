package com.thinking.my;

/**
 * Created by liyong on 2016/12/26.
 */
public interface ClassInInterface {

    void hoedy();

    class Test implements ClassInInterface{
        @Override
        public void hoedy() {
            System.out.println("Howdy!");
        }

        public static void main(String[] args){
            new Test().hoedy();
        }
    }
}
