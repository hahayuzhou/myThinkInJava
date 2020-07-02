package com.thinking.my.algorithm.lru;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description lru缓存
 * @Author liyong
 * @Date 2020/5/19 4:24 下午
 **/
public class LruCacher {
    public static final long INSTACE_CONTROL = 1;
    /**
     * 公平独占锁
     */
    private final ReentrantLock evictionLock = new ReentrantLock(true);
    /**
     * 是否在进程中 进行 驱逐 操作 默认 false
     */
    private volatile boolean evictionInProgress = false;

    private final AtomicLong size =  new AtomicLong(0);

    private long maxSize;
    //最小因子
    private float minFactor = 0.95f;
    private float acceptableFactor = 0.99f;
    private float singleFactor = 0.45f;
    private float multiFactor = 0.50f;
    private float residentFactor = 0.05f;

    private boolean instance = false;

    private EvictionThread eviction = null;

    //用ConcurrentHashMap 存放缓存对象
    private final Map<String,LruCachedEntry> map=null;

    private class EvictionThread implements Runnable {
        //指向缓存的弱引用
        private WeakReference<LruCacher> cacher;
        //
        private volatile boolean go = true;

        public EvictionThread(LruCacher cacher) {
            this.cacher = new WeakReference<LruCacher>(cacher);
        }


        @Override
        public void run() {
            while (this.go){
                //锁类成员对象
                synchronized (this){
                    try {
                        this.wait(1000L * 20);//20秒
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    LruCacher cache1 = this.cacher.get();
                    if (cache1 == null) {
                        break;
                    }
                    cache1.evict();
                }
            }
        }

        /**
         * 驱逐
         */
        public void evict() {
            synchronized (this) {
                this.notifyAll();
            }
        }
    }

    /**
     * 缓存驱逐算法
     * 需要竞争全局锁
     */
    private void evict() {
        if(!evictionLock.tryLock()){
            return;
        }
        try{
            evictionInProgress = true;
            long currentSize = this.size.get();
            long free = currentSize-minSize();
            if (free <= 0) {
                return;
            }
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
                    default:
                }
            }
        }catch (Exception e){

        }finally {

        }
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
        @Override
        public int compareTo(Bucket o) {
            return 0;
        }
    }
}
