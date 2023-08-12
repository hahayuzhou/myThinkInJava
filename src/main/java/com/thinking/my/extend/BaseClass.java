package com.thinking.my.extend;

public abstract class BaseClass implements BaseInterface {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class BaseBuilder {
        String Name;
    }
}
