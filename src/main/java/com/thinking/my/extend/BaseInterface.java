package com.thinking.my.extend;

public interface BaseInterface {

    int pageSize = 100;
    int PageNo = 1;

    public default Apple toApple(){
        return null;
    }

    int getPageSize();
}
