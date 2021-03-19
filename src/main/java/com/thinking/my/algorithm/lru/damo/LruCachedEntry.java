package com.thinking.my.algorithm.lru.damo;

public class LruCachedEntry implements Comparable<LruCachedEntry> {

	private final String key;
	private volatile Object value;
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

	public void access(long accessTime) {
		this.accessTime = accessTime;
		if (this.priority == LruPriority.single) {
			this.priority = LruPriority.multi;
		}
	}

	public boolean timeout() {
		return (System.currentTimeMillis() - cachedTime) >= timeout;
	}

	public long heapSize() {
		return size;
	}

	public int compareTo(LruCachedEntry that) {
		if (timeout()) return 1;
		else if (that.timeout()) return -1;
		if (this.accessTime == that.accessTime) return 0;
		return this.accessTime < that.accessTime ? 1 : -1;
	}

	public int hashCode() {
		return (int) (accessTime ^ (accessTime >>> 32));
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		LruCachedEntry other = (LruCachedEntry) obj;
		return compareTo(other) == 0;
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
