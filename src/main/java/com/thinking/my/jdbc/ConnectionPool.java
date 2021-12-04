package com.thinking.my.jdbc;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description jdbc 链接池 基于 org.apache.tomcat.jdbc.pool.ConnectionPool;
 * @Author liyong
 * @Date 2021/3/19 4:08 下午
 **/
public class ConnectionPool {

    //===============================================================================
    //         INSTANCE/QUICK ACCESS VARIABLE
    //===============================================================================
    /**
     * Carries the size of the pool, instead of relying on a queue implementation
     * that usually iterates over to get an exact count
     */
    private AtomicInteger size = new AtomicInteger(0);


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

    /**
     * The thread that is responsible for checking abandoned and idle threads
     */
    private volatile PoolCleaner poolCleaner;

    /**
     * Pool closed flag
     */
    private volatile boolean closed = false;

    /**
     * counter to track how many threads are waiting for a connection
     */
    private AtomicInteger waitcount = new AtomicInteger(0);

    private AtomicLong poolVersion = new AtomicLong(Long.MIN_VALUE);

    /**
     * The counters for statistics of the pool.
     */
    private final AtomicLong borrowedCount = new AtomicLong(0);
    private final AtomicLong returnedCount = new AtomicLong(0);
    private final AtomicLong createdCount = new AtomicLong(0);
    private final AtomicLong releasedCount = new AtomicLong(0);
    private final AtomicLong reconnectedCount = new AtomicLong(0);
    private final AtomicLong removeAbandonedCount = new AtomicLong(0);
    private final AtomicLong releasedIdleCount = new AtomicLong(0);

    /**
     * reference to the JMX mbean
     */
    protected com.thinking.my.jdbc.jmx.ConnectionPool jmxPool = null;

    public ConnectionPool(PoolConfiguration prop) throws SQLException {
        //setup quick access variables and pools
        init(prop);
    }

    private void init(PoolConfiguration properties) throws SQLException {
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
        //解析并创建初始的拦截器集。 让他们知道池已经开始
        //Parse and create an initial set of interceptors. Letting them know the pool has started.
        //These interceptors will not get any connection.
        PoolProperties.InterceptorDefinition[] proxies = getPoolProperties().getJdbcInterceptorsAsArray();
        for (int i=0; i<proxies.length; i++) {
            try {
                JdbcInterceptor interceptor = proxies[i].getInterceptorClass().getConstructor().newInstance();
                interceptor.setProperties(proxies[i].getProperties());
                interceptor.poolStarted(this);
            }catch (Exception x) {
                if (jmxPool!=null) jmxPool.notify(com.thinking.my.jdbc.jmx.ConnectionPool.NOTIFY_INIT, getStackTrace(x));
                close(true);
                SQLException ex = new SQLException();
                ex.initCause(x);
                throw ex;
            }
        }

        //initialize the pool with its initial set of members
        PooledConnection[] initialPool = new PooledConnection[poolProperties.getInitialSize()];
        try{
            for (int i = 0;i<initialPool.length;i++){
                initialPool[i] = this.borrowConnection(0, null, null); //don't wait, should be no contention
            }

        }catch (SQLException x){

        }



    }
    /**
     * Thread safe way to retrieve a connection from the pool
     * @param wait - time to wait, overrides the maxWait from the properties,
     * set to -1 if you wish to use maxWait, 0 if you wish no wait time.
     * @param username The user name to use for the connection
     * @param password The password for the connection
     * @return a connection
     * @throws SQLException Failed to get a connection
     */
    private PooledConnection borrowConnection(int wait, String username, String password) throws SQLException {
        if (isClosed()) {
            throw new SQLException("Connection pool closed.");
        }
        //get the current time stamp
        long now = System.currentTimeMillis();
        //see if there is one available immediately
        PooledConnection con = idle.poll();
        while (true) {
            if (con!=null) {
                //configure the connection and return it
                PooledConnection result = borrowConnection(now, con, username, password);
            }

        }

    }
    /**
     * 验证并配置以前空闲的连接
     * Validates and configures a previously idle connection
     * @param now - timestamp
     * @param con - the connection to validate and configure
     * @param username The user name to use for the connection
     * @param password The password for the connection
     * @return a connection
     * @throws SQLException if a validation error happens
     */
    private PooledConnection borrowConnection(long now, PooledConnection con, String username, String password) {
        //we have a connection, lets set it up

        //flag to see if we need to nullify
        //标记以查看是否需要取消
        boolean setToNull = false;
        try {
            con.lock();
            if (con.isReleased()) {
                return null;
            }
            //evaluate username/password change as well as max age functionality
            //评估用户名/密码更改以及最长期限功能
            boolean forceReconnect = con.shouldForceReconnect(username, password) || con.isMaxAgeExpired();

            if (!con.isDiscarded() && !con.isInitialized()) {
                //here it states that the connection not discarded, but the connection is null
                //don't attempt a connect here. It will be done during the reconnect.
                forceReconnect = true;
            }
            if (!forceReconnect) {
                if ((!con.isDiscarded()) && con.validate(PooledConnection.VALIDATE_BORROW)) {
                    //set the timestamp
//                    con.setTimestamp(now);
                    if (getPoolProperties().isLogAbandoned()) {
                        //set the stack trace for this pool
//                        con.setStackTrace(getThreadDump());
                    }
                    if (!busy.offer(con)) {
                    }
                    return con;
                }
            }
        }catch (Exception e){

        }
        return null;

    }

    /**
     * Closes the pool and all disconnects all idle connections
     * Active connections will be closed upon the {@link java.sql.Connection#close close} method is called
     * on the underlying connection instead of being returned to the pool
     * @param force - true to even close the active connections
     */
    protected void close(boolean force) {
        //are we already closed
        if (this.closed){
            return;
        }
        this.closed = true;
        //stop background thread
        if (poolCleaner!=null) {
            poolCleaner.stopRunning();
        }
        /* release all idle connections */
        BlockingQueue<PooledConnection> pool = (!idle.isEmpty())?idle:(force?busy:idle);

    }

    /**
     * Convert an exception into a String
     * @param x - the throwable
     * @return a string representing the stack trace
     */
    public static String getStackTrace(Throwable x) {
        if (x == null) {
            return null;
        } else {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            java.io.PrintStream writer = new java.io.PrintStream(bout);
            x.printStackTrace(writer);
            String result = bout.toString();
            return (x.getMessage()!=null && x.getMessage().length()>0)? x.getMessage()+";"+result:result;
        } //end if
    }

    private void createMBean() {
    }

    public PoolConfiguration getPoolProperties() {
        return this.poolProperties;
    }

    /**
     * Returns the total size of this pool, this includes both busy and idle connections
     * @return int - number of established connections to the database
     */
    public int getSize() {
        return size.get();
    }

    public void initializePoolCleaner(PoolConfiguration properties) {
    }

    public void checkPoolConfiguration(PoolConfiguration properties) {
    }

    /**
     * Returns the number of idle connections
     * @return int - number of established connections not being used
     */
    public int getIdle() {
        return idle.size();
    }

    /**
     * Returns the number of connections that are in use
     * @return int - number of established connections that are being used by the application
     */
    public int getActive() {
        return busy.size();
    }

    public int getWaitCount() {
        return waitcount.get();
    }

    public long getBorrowedCount() {
        return borrowedCount.get();
    }

    /**
     * The total number of connections returned to this pool.
     * @return the returned connection count
     */
    public long getReturnedCount() {
        return returnedCount.get();
    }

    /**
     * The total number of connections created by this pool.
     * @return the created connection count
     */
    public long getCreatedCount() {
        return createdCount.get();
    }

    /**
     * The total number of connections released from this pool.
     * @return the released connection count
     */
    public long getReleasedCount() {
        return releasedCount.get();
    }

    /**
     * The total number of connections reconnected by this pool.
     * @return the reconnected connection count
     */
    public long getReconnectedCount() {
        return reconnectedCount.get();
    }

    /**
     * The total number of connections released by remove abandoned.
     * @return the PoolCleaner removed abandoned connection count
     */
    public long getRemoveAbandonedCount() {
        return removeAbandonedCount.get();
    }

    /**
     * The total number of connections released by eviction.
     * @return the PoolCleaner evicted idle connection count
     */
    public long getReleasedIdleCount() {
        return releasedIdleCount.get();
    }

    /**
     * Iterates through the idle connections and resizes the idle pool based on parameters
     */
    public void checkIdle() {
        checkIdle(false);
    }

    private void checkIdle(boolean ignoreMinSize) {


    }

    public void checkAbandoned() {
    }

    public void testAllIdle() {
    }

    public void resetStats() {
    }

    public void terminatePoolCleaner() {
    }

    public void purge() {
    }

    public void purgeOnReturn() {
    }

    public void finalize(PooledConnection pooledConnection) {
    }

    protected static class PoolCleaner extends TimerTask {
        protected WeakReference<ConnectionPool> pool;
        protected long sleepTime;

        PoolCleaner(ConnectionPool pool,long sleepTime){
            this.pool = new WeakReference<>(pool);
            this.sleepTime = sleepTime;
            if (sleepTime <= 0) {
                this.sleepTime = 1000 * 30;
            }
        }
        @Override
        public void run() {
            ConnectionPool pool = this.pool.get();
            if (pool == null) {
                stopRunning();
            } else if (!pool.isClosed()) {
                try {
                    if (pool.getPoolProperties().isRemoveAbandoned()
                            || pool.getPoolProperties().getSuspectTimeout() > 0)
                        pool.checkAbandoned();
                    if (pool.getPoolProperties().getMinIdle() < pool.idle
                            .size())
                        pool.checkIdle();
                    if (pool.getPoolProperties().isTestWhileIdle())
                        pool.testAllIdle();
                } catch (Exception x) {
                }
            }

        }

        private void stopRunning() {
            unregisterCleaner(this);
        }
    }

    /**
     * Returns true if {@link #close close} has been called, and the connection pool is unusable
     * @return boolean
     */
    public  boolean isClosed() {
        return this.closed;
    }

    private static volatile Timer poolCleanTimer = null;
    private static HashSet<PoolCleaner> cleaners = new HashSet<>();
    private static synchronized void unregisterCleaner(PoolCleaner cleaner) {
        boolean removed = cleaners.remove(cleaner);
        if (removed){
            cleaner.cancel();
            if (poolCleanTimer != null) {
                poolCleanTimer.purge();
                if (cleaners.isEmpty()) {
                    poolCleanTimer.cancel();
                    poolCleanTimer = null;
                }
            }
        }
    }

    /**
     * Hook to perform final actions on a pooled connection object once it has been disconnected and will be discarded
     * @param con The connection
     * @param finalizing <code>true</code> if finalizing the connection
     */
    protected void disconnectEvent(PooledConnection con, boolean finalizing) {
        JdbcInterceptor handler = con.getHandler();
        while (handler!=null) {
            handler.disconnected(this, con, finalizing);
            handler=handler.getNext();
        }
    }

}
