package com.thinking.my.annnotation;

import java.lang.reflect.Field;

/**
 * Created by liyong on 2019/2/15.
 */
public class FruitInfoUtils {

    public static void getFruitInfo(Class<?> clazz)
    {
        String fruitNameStr = "";
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields)
        {
            if(field.isAnnotationPresent(FruitName.class))
            {
                FruitName fruitName = field.getAnnotation(FruitName.class);
                fruitNameStr = fruitName.value();
                System.out.println(fruitNameStr);
            }
        }
    }

    public static void main(String[] args) {

        getFruitInfo(Apple.class);
    }
}
