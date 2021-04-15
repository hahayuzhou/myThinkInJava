package com.thinking.my.jdbc;

import javax.management.ObjectName;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/19 4:35 下午
 **/
public class PooledConnection implements PooledConnectionMBean {

    /**
     * Logger
     */

    public static final String PROP_USER = PoolUtilities.PROP_USER;

    public static final String PROP_PASSWORD = PoolUtilities.PROP_PASSWORD;

    /**
     * Validate when connection is borrowed flag
     * 验证何时借用连接标志
     */
    public static final int VALIDATE_BORROW = 1;
    /**
     * Validate when connection is returned flag
     */
    public static final int VALIDATE_RETURN = 2;
    /**
     * Validate when connection is idle flag
     */
    public static final int VALIDATE_IDLE = 3;
    /**
     * Validate when connection is initialized flag
     */
    public static final int VALIDATE_INIT = 4;
    /**
     * The properties for the connection pool
     */
    protected PoolConfiguration poolProperties;
    /**
     * The underlying database connection
     */
    private volatile java.sql.Connection connection;

    /**
     * If using a XAConnection underneath.
     */
    protected volatile javax.sql.XAConnection xaConnection;
    /**
     * When we track abandon traces, this string holds the thread dump
     */
    private String abandonTrace = null;
    /**
     * Timestamp the connection was last 'touched' by the pool
     */
    private volatile long timestamp;
    /**
     * Lock for this connection only
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
    /**
     * Set to true if this connection has been discarded by the pool
     */
    private volatile boolean discarded = false;
    /**
     * The Timestamp when the last time the connect() method was called successfully
     */
    private volatile long lastConnected = -1;
    /**
     * timestamp to keep track of validation intervals
     */
    private volatile long lastValidated = System.currentTimeMillis();
    /**
     * The parent
     */
    protected ConnectionPool parent;

    private HashMap<Object, Object> attributes = new HashMap<>();

    private volatile long connectionVersion=0;

    private static final AtomicLong connectionIndex = new AtomicLong(0);

    private ObjectName oname = null;

    /**
     * Weak reference to cache the list of interceptors for this connection
     * so that we don't create a new list of interceptors each time we borrow
     * the connection
     */
    private volatile JdbcInterceptor handler = null;

    private AtomicBoolean released = new AtomicBoolean(false);

    private volatile boolean suspect = false;

    private java.sql.Driver driver = null;
    /**
     * Locks the connection only if either {@link PoolConfiguration#isPoolSweeperEnabled()} or
     * {@link PoolConfiguration#getUseLock()} return true. The per connection lock ensures thread safety is
     * multiple threads are performing operations on the connection.
     * Otherwise this is a noop for performance
     */
    public void lock() {
        if (poolProperties.getUseLock() || this.poolProperties.isPoolSweeperEnabled()) {
            //optimized, only use a lock when there is concurrency
            lock.writeLock().lock();
        }
    }

    @Override
    public long getConnectionVersion() {
        return 0;
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public boolean isMaxAgeExpired() {
        return false;
    }

    @Override
    public boolean isSuspect() {
        return false;
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public boolean isDiscarded() {
        return discarded;
    }

    @Override
    public long getLastValidated() {
        return 0;
    }

    @Override
    public long getLastConnected() {
        return 0;
    }

    /**
     * Returns true if this connection has been released and wont be reused.
     * @return true if the method {@link #release()} has been called
     */
    @Override
    public boolean isReleased() {
        return released.get();
    }

    /**
     * This method is called if (Now - timeCheckedIn &gt; getReleaseTime())
     * This method disconnects the connection, logs an error in debug mode if it happens
     * then sets the {@link #released} flag to false. Any attempts to connect this cached object again
     * will fail per {@link #connect()}
     * The connection pool uses the atomic return value to decrement the pool size counter.
     * @return true if this is the first time this method has been called. false if this method has been called before.
     */
    public boolean release() {
        try {
            disconnect(true);
        } catch (Exception x) {
        }
        if (oname != null) {
            JmxUtil.unregisterJmx(oname);
            oname = null;
        }
        return released.compareAndSet(false, true);

    }

    /**
     * Disconnects the connection. All exceptions are logged using debug level.
     * @param finalize if set to true, a call to {@link ConnectionPool#finalize(PooledConnection)} is called.
     */
    private void disconnect(boolean finalize) {
        if (isDiscarded() && connection == null) {
            return;
        }
        setDiscarded(true);
        if (connection != null) {
            try {
                parent.disconnectEvent(this, finalize);
                if (xaConnection == null) {
                    connection.close();
                } else {
                    xaConnection.close();
                }
            }catch (Exception ignore) {
            }
        }
        connection = null;
        xaConnection = null;
        lastConnected = -1;
        if (finalize) parent.finalize(this);

    }

    /**
     * An interceptor can call this method with the value true, and the connection will be closed when it is returned to the pool.
     * @param discarded - only valid value is true
     * @throws IllegalStateException if this method is called with the value false and the value true has already been set.
     */
    public void setDiscarded(boolean discarded) {
        if (this.discarded && !discarded) throw new IllegalStateException("Unable to change the state once the connection has been discarded");
        this.discarded = discarded;
    }

    /**
     * Connects the underlying connection to the database.
     * @throws SQLException if the method {@link #release()} has been called.
     * @throws SQLException if driver instantiation fails
     * @throws SQLException if a call to {@link java.sql.Driver#connect(String, java.util.Properties)} fails.
     * @throws SQLException if default properties are configured and a call to
     * {@link java.sql.Connection#setAutoCommit(boolean)}, {@link java.sql.Connection#setCatalog(String)},
     * {@link java.sql.Connection#setTransactionIsolation(int)} or {@link java.sql.Connection#setReadOnly(boolean)} fails.
     */
    public void connect() throws SQLException {

    }


    @Override
    public void clearWarnings() {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    /**
     * Returns the first handler in the interceptor chain
     * @return the first interceptor for this connection
     */
    public JdbcInterceptor getHandler() {
        return handler;
    }

    /**
     * Returns true if we must force reconnect based on credentials passed in.
     * Returns false if {@link PoolConfiguration#isAlternateUsernameAllowed()} method returns false.
     * Returns false if the username/password has not changed since this connection was connected
     * @param username the username you wish to connect with, pass in null to accept the default username from {@link PoolConfiguration#getUsername()}
     * @param password the password you wish to connect with, pass in null to accept the default username from {}
     * @return true is the pool must reconnect
     */
    public boolean shouldForceReconnect(String username, String password) {

        if (!getPoolProperties().isAlternateUsernameAllowed()) return false;

        if (username==null) username = poolProperties.getUsername();
        if (password==null) password = poolProperties.getPassword();

        String storedUsr = (String)getAttributes().get(PROP_USER);
        String storedPwd = (String)getAttributes().get(PROP_PASSWORD);

        boolean noChangeInCredentials = (username==null && storedUsr==null);
        noChangeInCredentials = (noChangeInCredentials || (username!=null && username.equals(storedUsr)));

        noChangeInCredentials = noChangeInCredentials && ((password==null && storedPwd==null) || (password!=null && password.equals(storedPwd)));

        if (username==null)  getAttributes().remove(PROP_USER); else getAttributes().put(PROP_USER, username);
        if (password==null)  getAttributes().remove(PROP_PASSWORD); else getAttributes().put(PROP_PASSWORD, password);

        return !noChangeInCredentials;
    }

    /**
     * Returns the configuration for this connection and pool
     * @return the configuration for this connection and pool
     */
    public PoolConfiguration getPoolProperties() {
        return poolProperties;
    }

    public HashMap<Object,Object> getAttributes() {
        return attributes;
    }

    /**
     * Returns <code>true</code> if the object is still valid. if not
     * the pool will call the getExpiredAction() and follow up with one
     * of the four expired methods
     * @param validateAction The value
     * @return <code>true</code> if the connection is valid
     */
    public boolean validate(int validateAction) {
        return validate(validateAction,null);
    }

    /**
     * Validates a connection.
     * @param validateAction the action used. One of {@link #VALIDATE_BORROW}, {@link #VALIDATE_IDLE},
     * {@link #VALIDATE_INIT} or {@link #VALIDATE_RETURN}
     * @param sql the SQL to be used during validation. If the {@link PoolConfiguration#setInitSQL(String)} has been called with a non null
     * value and the action is {@link #VALIDATE_INIT} the init SQL will be used for validation.
     *
     * @return true if the connection was validated successfully. It returns true even if validation was not performed, such as when
     * {@link PoolConfiguration#setValidationInterval(long)} has been called with a positive value.
     * <p>
     * false if the validation failed. The caller should close the connection if false is returned since a session could have been left in
     * an unknown state during initialization.
     */
    public boolean validate(int validateAction,String sql) {
        if (this.isDiscarded()) {
            return false;
        }
        if (!doValidate(validateAction)) {
            //no validation required, no init sql and props not set
            return true;
        }

    }
    /**
     * Returns <code>true</code> if the connection pool is configured
     * to do validation for a certain action.
     * @param action The validation action
     */
    private boolean doValidate(int action) {
        if (action == PooledConnection.VALIDATE_BORROW &&
                poolProperties.isTestOnBorrow())
            return true;
        else if (action == PooledConnection.VALIDATE_RETURN &&
                poolProperties.isTestOnReturn())
            return true;
        else if (action == PooledConnection.VALIDATE_IDLE &&
                poolProperties.isTestWhileIdle())
            return true;
        else if (action == PooledConnection.VALIDATE_INIT &&
                poolProperties.isTestOnConnect())
            return true;
        else if (action == PooledConnection.VALIDATE_INIT &&
                poolProperties.getInitSQL()!=null)
            return true;
        else
            return false;
    }

}
