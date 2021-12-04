package com.thinking.my.algorithm.queue;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @Description
 * @Author liyong
 * @Date 2021/11/25 9:05 下午
 **/
public class MinStack {
    Stack<Integer> a,b;
    public MinStack(){
        a = new Stack();
        b = new Stack();
    }

    public void push(int x) {
        a.push(x);
        if(b.isEmpty()){
            b.push(x);
        }else{
            if(x<=b.peek()){
                b.push(x);
            }
        }

    }

    public void pop() {
        if(b.peek().equals(a.pop())){
            b.pop();
        }

    }

    public int top() {
      return a.peek();
    }

    public int min() {
        return b.peek();
    }

    public static void main(String[] args) {
        int[] aa = new int[0];
        System.out.println(aa.toString());

        int[] bb = {1,2};
        System.out.println(bb.toString());

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(2);
        System.out.println(arrayList);



    }



}
