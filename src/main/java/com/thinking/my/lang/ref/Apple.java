package com.thinking.my.lang.ref;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/22 8:17 下午
 **/
public class Apple {
    private String name;

    public Apple(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                '}';
    }
}
