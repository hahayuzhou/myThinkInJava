package com.thinking.my.algorithm.lru.damo;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class LruCacher {

	private final Map<String, LruCachedEntry> map;
	private final ReentrantLock evictionLock = new ReentrantLock(true);
	private volatile boolean evictionInProgress = false;
	private EvictionThread eviction = null;
	private Thread evictionThread;

	private final AtomicLong size;
	private final AtomicLong elements;
	private final AtomicLong count;
	private long maxSize;
	private float minFactor = 0.95f;
	private float acceptableFactor = 0.99f;
	private float singleFactor = 0.45f;
	private float multiFactor = 0.50f;
	private float residentFactor = 0.05f;
	private boolean instance = false;
	public static final long INSTACE_CONTROL = 1;
	private static LruCacher cacher = null;

	public static LruCacher getInstance(long maxSize, boolean instance) {
    	if (cacher == null) {
    		synchronized (LruCacher.class) {
        		if (cacher == null) {
        			cacher = new LruCacher(maxSize, instance);
            	}
        	}
    	}
    	return cacher;
    }

	public static LruCacher getInstance(long maxSize, boolean asyncEvict, boolean instance) {
    	if (cacher == null) {
    		synchronized (LruCacher.class) {
        		if (cacher == null) {
        			cacher = new LruCacher(maxSize, asyncEvict, instance);
            	}
        	}
    	}
    	return cacher;
    }

	private LruCacher(long maxSize, boolean instance) {
		this(maxSize, false, instance);
	}

	private LruCacher(long maxSize, boolean asyncEvict, boolean instance) {
		if (Math.abs(singleFactor + multiFactor + residentFactor - 1) > 0.0001 || singleFactor < 0 || multiFactor < 0 || residentFactor < 0) {
			throw new IllegalArgumentException("single, multi, and resident factors " + " should be non-negative and total 1.0");
		}
		this.maxSize = maxSize;
		if (instance) {
			map = new ConcurrentHashMap<String, LruCachedEntry>((int)(maxSize / 1024));
		} else {
			map = new ConcurrentHashMap<String, LruCachedEntry>((int)(maxSize / (1024 * 1024)));
		}
		this.instance = instance;
		this.count = new AtomicLong(0);
		this.elements = new AtomicLong(0);
		this.size = new AtomicLong(0);
		if (asyncEvict) {
			this.eviction = new EvictionThread(this);
			this.evictionThread = new Thread(eviction);
			this.evictionThread.setDaemon(true);
			this.evictionThread.start();
		}
	}

	public void put(String key, Object value, long timeout) {
		put(key, value, INSTACE_CONTROL, false, timeout);
	}

	public void put(String key, Object value, boolean resident, long timeout) {
		put(key, value, INSTACE_CONTROL, resident, timeout);
	}

	public void put(String key, Object value, long size, long timeout) {
		put(key, value, size, false, timeout);
	}

	public void put(String key, Object value, long size, boolean resident, long timeout) {
		if (StringUtils.isBlank(key) || value == null) return;
		LruCachedEntry cb = map.get(key);
		if (cb != null) {
			updateSizeMetrics(cb, true);
			cb.setValue(value, size, resident);
			cb.access(count.incrementAndGet());
			updateSizeMetrics(cb, false);
			return;
		}
		cb = new LruCachedEntry(key, value, size, count.incrementAndGet(), resident, timeout * 1000);
		long newSize = updateSizeMetrics(cb, false);
		map.put(key, cb);
		elements.incrementAndGet();
		if (newSize > acceptableSize() && !evictionInProgress) {
			runEviction();
		}
	}

	protected long updateSizeMetrics(LruCachedEntry cb, boolean evict) {
		long heapsize = cb.heapSize();
		if (evict) heapsize *= -1;
		return size.addAndGet(heapsize);
	}

	public Object get(String key) {
		LruCachedEntry cb = map.get(key);
		if (cb == null) return null;
		if (cb.timeout()) {
			evict(cb);
			return null;
		}
		cb.access(count.incrementAndGet());
		return cb.getValue();
	}

	public boolean contains(String key) {
		return map.containsKey(key);
	}

	public boolean evict(String key) {
		LruCachedEntry cb = map.get(key);
		if (cb == null) return false;
		evict(cb);
		return true;
	}

	protected long evict(LruCachedEntry entry) {
		map.remove(entry.getKey());
		updateSizeMetrics(entry, true);
		return entry.heapSize();
	}

	private void runEviction() {
		if (evictionThread == null) {
			evict();
		} else {
			eviction.evict();
		}
	}

	void evict() {
		if (!evictionLock.tryLock()) return;
		try {
			evictionInProgress = true;
			long currentSize = this.size.get();
			long free = currentSize - minSize();
			if (free <= 0) return;
			Bucket bucketSingle = new Bucket(free, singleSize());
			Bucket bucketMulti = new Bucket(free, multiSize());
			Bucket bucketResident = new Bucket(free, residentSize());
			for (LruCachedEntry entry : map.values()) {
				switch (entry.getPriority()) {
				case single: {
					bucketSingle.add(entry);
					break;
				}
				case multi: {
					bucketMulti.add(entry);
					break;
				}
				case resident: {
					bucketResident.add(entry);
					break;
				}
				}
			}
			long bytesFreed = 0;
			PriorityQueue<Bucket> bucketQueue = new PriorityQueue<Bucket>(3);
			bucketQueue.add(bucketSingle);
			bucketQueue.add(bucketMulti);
			bucketQueue.add(bucketResident);
			int remainingBuckets = 3;
			Bucket bucket;
			while ((bucket = bucketQueue.poll()) != null) {
				long overflow = bucket.overflow();
				if (overflow > 0) {
					long bucketToFree = Math.min(overflow, (free - bytesFreed) / remainingBuckets);
					bytesFreed += bucket.free(bucketToFree);
				}
				remainingBuckets--;
			}
		} finally {
			evictionInProgress = false;
			evictionLock.unlock();
		}
	}

	private class Bucket implements Comparable<Bucket> {

		private LruCachedQueue queue;
		private long totalSize = 0;
		private long bucketSize;

		public Bucket(long toFree, long bucketSize) {
			this.bucketSize = bucketSize;
			this.queue = new LruCachedQueue(toFree, instance);
			this.totalSize = 0;
		}

		public void add(LruCachedEntry entry) {
			totalSize += entry.heapSize();
			queue.add(entry);
		}

		public long free(long toFree) {
			LruCachedEntry cb;
			long freedBytes = 0;
			while ((cb = queue.pollLast()) != null) {
				freedBytes += evict(cb);
				if (freedBytes >= toFree) {
					return freedBytes;
				}
			}
			return freedBytes;
		}

		public long overflow() {
			return totalSize - bucketSize;
		}

		public int compareTo(Bucket that) {
			return Long.compare(this.overflow(), that.overflow());
		}

		public boolean equals(Object that) {
			if (that == null || !(that instanceof Bucket)) {
				return false;
			}
			return compareTo((Bucket) that) == 0;
		}

		public int hashCode() {
			int result = queue.hashCode();
			return result;
		}

	}

	public long getMaxSize() {
		return this.maxSize;
	}

	public long getCurrentSize() {
		return this.size.get();
	}

	public long getFreeSize() {
		return getMaxSize() - getCurrentSize();
	}

	public long size() {
		return getMaxSize();
	}

	long acceptableSize() {
		return (long) Math.floor(this.maxSize * this.acceptableFactor);
	}

	private long minSize() {
		return (long) Math.floor(this.maxSize * this.minFactor);
	}

	private long singleSize() {
		return (long) Math.floor(this.maxSize * this.singleFactor * this.minFactor);
	}

	private long multiSize() {
		return (long) Math.floor(this.maxSize * this.multiFactor * this.minFactor);
	}

	private long residentSize() {
		return (long) Math.floor(this.maxSize * this.residentFactor * this.minFactor);
	}

	EvictionThread getEvictionThread() {
		return this.eviction;
	}

	static class EvictionThread implements Runnable {

		private WeakReference<LruCacher> cache;
		private volatile boolean go = true;

		public EvictionThread(LruCacher cache) {
			this.cache = new WeakReference<LruCacher>(cache);
		}

		public void run() {
			while (this.go) {
				synchronized (this) {
					try {
						this.wait(1000 * 10);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				LruCacher cache = this.cache.get();
				if (cache == null) break;
				cache.evict();
			}
		}

		public void evict() {
			synchronized (this) {
				this.notifyAll();
			}
		}

		synchronized void shutdown() {
			this.go = false;
			this.notifyAll();
		}

	}

	public void shutdown() {
		this.eviction.shutdown();
	}

	public void clearCache() {
		this.map.clear();
		this.elements.set(0);
	}

}
