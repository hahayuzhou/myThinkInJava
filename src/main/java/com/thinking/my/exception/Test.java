package com.thinking.my.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liyong40
 * @Description
 * @date 2022/11/1
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {

        String a = "c";
      try{
          StringToInt(a);
      } catch (Exception e){
          System.out.println("--");
          logger.error("Error in transaction:{}",e.getMessage());
          System.out.println("--");
          System.out.println("--");
          logger.error("Error:{}", e);
          System.out.println("--");
          System.out.println(e.getMessage());
          System.out.println("--");
//          e.printStackTrace();
      }

    }

    public static Integer StringToInt(String c){
        return  Integer.parseInt(c);
    }
}
