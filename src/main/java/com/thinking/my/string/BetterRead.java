package com.thinking.my.string;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by liyong on 2017/2/13.
 */
public class BetterRead {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(SimpleRead.input);


            System.out.println("what is your name?");
            String name = stdin.nextLine();
            System.out.println(name);

            int age = stdin.nextInt();
            double favorite = stdin.nextDouble();
            System.out.format("Hi %s. \n",name);
            System.out.format("In 5 years you will be %d. \n",age +5);
            System.out.format("My favorite double is %f. \n",favorite/2);




    }
}
