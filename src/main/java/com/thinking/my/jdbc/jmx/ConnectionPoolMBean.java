package com.thinking.my.jdbc.jmx;

import com.thinking.my.jdbc.PoolConfiguration;

public interface ConnectionPoolMBean extends PoolConfiguration {

    //=================================================================
    //       POOL STATS
    //=================================================================

    public int getSize();

    public int getIdle();

    public int getActive();

    public int getNumIdle();

    public int getNumActive();

    public int getWaitCount();

    public long getBorrowedCount();

    public long getReturnedCount();

    public long getCreatedCount();

    public long getReleasedCount();

    public long getReconnectedCount();

    public long getRemoveAbandonedCount();

    public long getReleasedIdleCount();

    //=================================================================
    //       POOL OPERATIONS
    //=================================================================
    public void checkIdle();

    public void checkAbandoned();

    public void testIdle();

    /**
     * Purges all connections in the pool.
     * For connections currently in use, these connections will be
     * purged when returned on the pool. This call also
     * purges connections that are idle and in the pool
     * To only purge used/active connections see {@link #purgeOnReturn()}
     */
    public void purge();

    /**
     * Purges connections when they are returned from the pool.
     * This call does not purge idle connections until they are used.
     * To purge idle connections see {@link #purge()}
     */
    public void purgeOnReturn();

    /**
     * reset the statistics of this pool.
     */
    public void resetStats();

    //=================================================================
    //       POOL NOTIFICATIONS
    //=================================================================
}
