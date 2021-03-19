//package com.thinking.my.buffer;
//
//import hwb.Pool;
//
//import java.nio.ByteBuffer;
//import java.util.ArrayDeque;
//import java.util.Deque;
//import java.util.concurrent.locks.AbstractQueuedSynchronizer;
//
///**
// * @author huangwenbo
// * @date 2020/9/24 1:03 下午
// */
//public class BufferPoolSyncSimpleShare implements Pool {
//
//    private final SyncAllocate syncAllocate;
//
//    private final long totalMemory;
//    private final int poolableSize;
//
//    private final Deque<ByteBuffer> free;
//    /** Total available memory is the sum of nonPooledAvailableMemory and the number of byte buffers in free * poolableSize.  */
//
//    // copy from ThreadPoolExecutor
//    private static int COUNT_BITS;
//    private static int NON_POOLED_AVAILABLE_MEMORY_CAPACITY;
//
//
//    private static int poolableCountOf(int c)     { return (c & ~NON_POOLED_AVAILABLE_MEMORY_CAPACITY) >> COUNT_BITS; }
//    private static int nonPooledAvailableMemoryOf(int c)  { return c & NON_POOLED_AVAILABLE_MEMORY_CAPACITY; }
//    private static int ctlOf(int pc, int nm) { return (pc << COUNT_BITS) | nm; }
//
//
//    public BufferPoolSyncSimpleShare(int memory, int poolableSize) {
//        int maxPoolableCount = memory / poolableSize + 1;
//        int poolableCountBitCount = 0;
//        for (int i = 0; i < Integer.SIZE; i++) {
//            if (maxPoolableCount < 1 << i) {
//                poolableCountBitCount = i;
//                break;
//            }
//        }
//        // 32位，前poolableCountBitCount位放poolableCount，后COUNT_BITS位放NON_POOLED_AVAILABLE_MEMORY
//        COUNT_BITS = Integer.SIZE - poolableCountBitCount;
//        NON_POOLED_AVAILABLE_MEMORY_CAPACITY = (1 << COUNT_BITS) - 1;
//
//        if (memory > NON_POOLED_AVAILABLE_MEMORY_CAPACITY) {
//            throw new IllegalArgumentException("overflow, increase poolableSize or decrease memory");
//        }
//
//        this.syncAllocate = new SyncAllocate(ctlOf(0, memory), poolableSize);
//
//        this.poolableSize = poolableSize;
//        this.free = new ArrayDeque<>();
//        this.totalMemory = memory;
//    }
//
//
//    static class SyncAllocate extends AbstractQueuedSynchronizer {
//
//        private final int poolableSize;
//
//        public SyncAllocate(int permits, int poolableSize) {
//            setState(permits);
//            this.poolableSize = poolableSize;
//        }
//
//        @Override
//        protected int tryAcquireShared(int acquires) {
//            if (hasQueuedPredecessors())
//                return -1;
//
//            int ctl = getState();
//            int poolableCount = poolableCountOf(ctl);
//            while (acquires == poolableSize && poolableCount > 0) {
//                int remaining = poolableCount - acquires;
//                final int update = ctlOf(remaining, nonPooledAvailableMemoryOf(ctl));
//                if (update < 0 ||
//                        compareAndSetState(ctl, update))
//                    return update;
//
//                ctl = getState();
//                poolableCount = poolableCountOf(ctl);
//            }
//
//            freeUp(acquires);
//
//            ctl = getState();
//            int nonPooledAvailableMemory = nonPooledAvailableMemoryOf(ctl);
//            while (nonPooledAvailableMemory >= acquires) {
//                final int update = ctlOf(poolableCountOf(ctl), nonPooledAvailableMemory - acquires);
//                if (update < 0 ||
//                        compareAndSetState(ctl, update))
//                    return update;
//
//                ctl = getState();
//                nonPooledAvailableMemory = nonPooledAvailableMemoryOf(ctl);
//            }
//            return -1;
//        }
//
//        private void freeUp(int acquires) {
//            int ctl = getState();
//            int oldPoolableCount = poolableCountOf(ctl);
//            int poolableCount = poolableCountOf(ctl);
//            int nonPooledAvailableMemory = nonPooledAvailableMemoryOf(ctl);
//            while (poolableCount > 0 && nonPooledAvailableMemory < acquires) {
//                compareAndSetState(ctl, ctlOf(poolableCount - 1, nonPooledAvailableMemory + poolableSize));
//
//                ctl = getState();
//                poolableCount = poolableCountOf(ctl);
//                nonPooledAvailableMemory = nonPooledAvailableMemoryOf(ctl);
//            }
//            final int size = oldPoolableCount - poolableCount;
//            if (size > 0)
//                System.out.println("free up size : " + size);
//        }
//
//        @Override
//        protected final boolean tryReleaseShared(int releases) {
//            if (releases == poolableSize) {
//                while (true) {
//                    int ctl = getState();
//                    final int update = ctlOf(poolableCountOf(ctl) + 1, nonPooledAvailableMemoryOf(ctl));
//                    if (update < ctl) // overflow
//                        throw new Error("Maximum permit count exceeded");
//                    if (compareAndSetState(ctl, update)) {
//                        return true;
//                    }
//                }
//            }
//
//            while (true) {
//                int ctl = getState();
//                final int update = ctlOf(poolableCountOf(ctl), nonPooledAvailableMemoryOf(ctl) + releases);
//                if (update < ctl) // overflow
//                    throw new Error("Maximum permit count exceeded");
//                if (compareAndSetState(ctl, update))
//                    return true;
//            }
//        }
//    }
//
//
//    public ByteBuffer allocate(int size, long maxTimeToBlockMs) {
//        if (size > this.totalMemory)
//            throw new IllegalArgumentException("Attempt to allocate " + size
//                    + " bytes, but there is a hard limit of " + this.totalMemory + " on memory allocations.");
//
//        syncAllocate.acquireShared(size);
//
//        System.out.println("thread : " + Thread.currentThread().getName() + " got " + size);
//        if (size == poolableSize && !free.isEmpty()) {
//            return free.pollFirst();
//        }
//        return safeAllocateByteBuffer(size);
//    }
//
//    public void deallocate(ByteBuffer buffer) {
//        int size = buffer.capacity();
//        syncAllocate.releaseShared(size);
//
//        System.out.println("thread : " + Thread.currentThread().getName() + "release " + size);
//
//        if (size == poolableSize) {
//            buffer.clear();
//            this.free.add(buffer);
//        }
//    }
//
//    private ByteBuffer safeAllocateByteBuffer(int size) {
//        return ByteBuffer.allocate(size);
//    }
//}
