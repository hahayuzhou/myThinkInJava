package com.thinking.my.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class FactoryMain {

    public static void main(String[] args) {
        Product p1 = ProductFactory.createProduct("loan");

        Supplier<Product> loanSupplier = Loan::new;
        Product p2 = loanSupplier.get();

        Product p3 = ProductFactory.createProductLambda("loan");
        Product p4 = ProductFactory.createProductLambda("loan");
        System.out.println(p3.equals(p4));
        String p33 = ProductFactory.createStringLambda("loan");
        String p43 = ProductFactory.createStringLambda("loan");
        System.out.println(p33.equals(p43));

    }

    static private class ProductFactory {
        public static Product createProduct(String name){
            switch(name){
                case "loan": return new Loan();
                case "stock": return new Stock();
                case "bond": return new Bond();
                default: throw new RuntimeException("No such product " + name);
            }
        }

        public static Product createProductLambda(String name){
            Supplier<Product> p = map.get(name);
            if(p != null) return p.get();
            throw new RuntimeException("No such product " + name);
        }
        public static String createStringLambda(String name){
            Supplier<String> p = map2.get(name);
            if(p != null) return p.get();
            throw new RuntimeException("No such product " + name);
        }
    }

    static private interface Product {}
    static private class Loan implements Product {
        String a = "1";
    }
    static private class Stock implements Product {}
    static private class Bond implements Product {}

    final static private Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);//存放的是构造函数
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }
    final static private Map<String, Supplier<String>> map2 = new HashMap<>();
    static {
        map2.put("loan", String ::new);//存放的是构造函数
        map2.put("stock", String ::new);
        map2.put("bond",  String ::new);
    }
}
