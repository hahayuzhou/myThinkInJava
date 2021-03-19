package com.thinking.my.jdbc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description jdbc 链接池 基于 org.apache.tomcat.jdbc.pool.ConnectionPool;
 * @Author liyong
 * @Date 2021/3/19 4:08 下午
 **/
public class ConnectionPool {

    /**
     * All the information about the connection pool
     * These are the properties the pool got instantiated with
     */
    private PoolConfiguration poolProperties;

    /**
     * Contains all the connections that are in use
     * TODO - this shouldn't be a blocking queue, simply a list to hold our objects
     */
    private BlockingQueue<PooledConnection> busy;

    /**
     * Contains all the idle connections
     */
    private BlockingQueue<PooledConnection> idle;

    public ConnectionPool(PoolConfiguration prop) {
        //setup quick access variables and pools
        init(prop);
    }

    private void init(PoolConfiguration properties) {
        poolProperties = properties;
        //make sure the pool is properly configured
        checkPoolConfiguration(properties);

        busy = new LinkedBlockingQueue<>();

        if (properties.isFairQueue()) {
            idle = new FairBlockingQueue<>();
            //idle = new MultiLockFairBlockingQueue<PooledConnection>();
            //idle = new LinkedTransferQueue<PooledConnection>();
            //idle = new ArrayBlockingQueue<PooledConnection>(properties.getMaxActive(),false);
        } else {
            idle = new LinkedBlockingQueue<>();
        }

        initializePoolCleaner(properties);

        //create JMX MBean
        if (this.getPoolProperties().isJmxEnabled()){
            createMBean();
        }

        //Parse and create an initial set of interceptors. Letting them know the pool has started.
        //These interceptors will not get any connection.
        PoolProperties.InterceptorDefinition[] proxies = getPoolProperties().getJdbcInterceptorsAsArray();


    }

    private void createMBean() {
    }

    private PoolConfiguration getPoolProperties() {
        return this.poolProperties;
    }

    private void initializePoolCleaner(PoolConfiguration properties) {
    }

    private void checkPoolConfiguration(PoolConfiguration properties) {
    }
}
