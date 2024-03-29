package com.thinking.my.jdbc;

import java.util.Properties;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/22 7:51 下午
 **/
public class PoolUtilities {
    public static final String PROP_USER = "user";

    public static final String PROP_PASSWORD = "password";

    public static Properties clone(Properties p) {
        Properties c = new Properties();
        c.putAll(p);
        return c;
    }

    public static Properties cloneWithoutPassword(Properties p) {
        Properties result = clone(p);
        result.remove(PROP_PASSWORD);
        return result;
    }
}
