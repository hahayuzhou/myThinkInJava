package com.thinking.my.jdbc;


import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/19 5:44 下午
 **/
public class DataSourceFactory implements ObjectFactory {

    public static final int UNKNOWN_TRANSACTIONISOLATION = -1;
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return null;
    }
}
