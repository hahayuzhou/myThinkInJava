package com.thinking.my.lang.ref;

import java.lang.ref.WeakReference;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/22 8:18 下午
 **/
public class MyObjectWeekReference extends WeakReference<MyObject> {

    public MyObjectWeekReference(MyObject referent) {
        super(referent);
    }
}
