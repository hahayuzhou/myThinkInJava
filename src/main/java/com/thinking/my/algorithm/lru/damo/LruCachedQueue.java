package com.thinking.my.algorithm.lru.damo;

import com.google.common.collect.MinMaxPriorityQueue;

public class LruCachedQueue {

	private MinMaxPriorityQueue<LruCachedEntry> queue;
	private long heapSize;
	private long maxSize;

	public LruCachedQueue(long maxSize, boolean instance) {
		int initialSize = (int) (maxSize / 2);
		if (!instance) initialSize = (int) (maxSize / (2 * 1024 * 1024));
		if (initialSize == 0) initialSize++;
		queue = MinMaxPriorityQueue.expectedSize(initialSize).create();
		this.heapSize = 0;
		this.maxSize = maxSize;
	}

	public void add(LruCachedEntry cb) {
		if (heapSize < maxSize) {
			queue.add(cb);
			heapSize += cb.heapSize();
		} else {
			LruCachedEntry head = queue.peek();
			if (cb.compareTo(head) > 0) {
				heapSize += cb.heapSize();
				heapSize -= head.heapSize();
				if (heapSize > maxSize) {
					queue.poll();
				} else {
					heapSize += head.heapSize();
				}
				queue.add(cb);
			}
		}
	}

	public LruCachedEntry poll() {
		return queue.poll();
	}

	public LruCachedEntry pollLast() {
		return queue.pollLast();
	}

	public long heapSize() {
		return heapSize;
	}

}
