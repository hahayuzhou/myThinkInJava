package com.thinking.my.algorithm.lru;

/**
 * @Description
 * @Author liyong
 * @Date 2020/5/19 4:24 下午
 **/
public class LruCachedEntry implements Comparable<LruCachedEntry> {

    private final String key;
    private volatile Object value;
    //访问时间
    private volatile long accessTime;
    private volatile long size;
    private volatile LruPriority priority;
    private long timeout = 300000; //5mins
    private long cachedTime = System.currentTimeMillis();

    public LruCachedEntry(String key, Object value, long accessTime, long timeout) {
        this(key, value, LruCacher.INSTACE_CONTROL, accessTime, false, timeout);
    }

    public LruCachedEntry(String key, Object value, long size, long accessTime, boolean resident, long timeout) {
        this.key = key;
        this.value = value;
        this.accessTime = accessTime;
        this.size = size;
        this.timeout = timeout;
        if (resident) {
            this.priority = LruPriority.resident;
        } else {
            this.priority = LruPriority.single;
        }
    }

    /**
     * 访问 修改访问时间
     *
     * @param accessTime
     */
    public void access(long accessTime) {
        this.accessTime = accessTime;
        if (this.priority == LruPriority.single) {//如果是单次访问 改为多次访问
            this.priority = LruPriority.multi;
        }
    }

    public boolean timeout() {
        return (System.currentTimeMillis() - cachedTime) >= timeout;
    }

    @Override
    public int compareTo(LruCachedEntry o) {
        if (timeout()) {//如果超时 返回1
            return 1;
        }
        if (o.timeout()) {
            return -1;
        }
        if (this.accessTime == o.accessTime) {
            return 0;
        }
        return this.accessTime < o.accessTime ? 1 : -1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return compareTo((LruCachedEntry) obj) == 0;
    }

    public long heapSize() {
        return size;
    }

    public Object getValue() {
        return this.value;
    }

    public String getKey() {
        return this.key;
    }

    public LruPriority getPriority() {
        return this.priority;
    }

    public synchronized void setValue(Object value, long size, boolean resident) {
        this.value = value;
        this.size = size;
        if (resident && !(this.priority == LruPriority.resident)) {
            this.priority = LruPriority.resident;
        } else if (!resident && (this.priority == LruPriority.resident)) {
            this.priority = LruPriority.single;
        }
    }
}

