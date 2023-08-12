package com.thinking.my.regex.sql.bean;

/**
 * @author liyong40
 * @Description
 * @date 2022/12/20
 */
public class TableInfo {

    private String name;
    private String alias;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public TableInfo(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public TableInfo() {
    }
}
