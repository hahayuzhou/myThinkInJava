package com.thinking.my.jdbc;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/19 4:39 下午
 **/
public class FairBlockingQueue<E> implements java.util.concurrent.BlockingQueue<PooledConnection> {


    /**
     * All the objects in the pool are stored in a simple linked list
     */
    final LinkedList<E> items;

    /**
     * All threads waiting for an object are stored in a linked list
     */
    final LinkedList<ExchangeCountDownLatch<E>> waiters;

    /**
     * Creates a new fair blocking queue.
     */
    public FairBlockingQueue() {
        items = new LinkedList<>();
        waiters = new LinkedList<>();
    }

    @Override
    public boolean add(PooledConnection pooledConnection) {
        return false;
    }

    @Override
    public boolean offer(PooledConnection pooledConnection) {
        return false;
    }

    @Override
    public PooledConnection remove() {
        return null;
    }

    @Override
    public PooledConnection poll() {
        return null;
    }

    @Override
    public PooledConnection element() {
        return null;
    }

    @Override
    public PooledConnection peek() {
        return null;
    }

    @Override
    public void put(PooledConnection pooledConnection) throws InterruptedException {

    }

    @Override
    public boolean offer(PooledConnection pooledConnection, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public PooledConnection take() throws InterruptedException {
        return null;
    }

    @Override
    public PooledConnection poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends PooledConnection> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<PooledConnection> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public int drainTo(Collection<? super PooledConnection> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super PooledConnection> c, int maxElements) {
        return 0;
    }

    private class ExchangeCountDownLatch<E> extends CountDownLatch {
        /**
         * Constructs a {@code CountDownLatch} initialized with the given count.
         *
         * @param count the number of times {@link #countDown} must be invoked
         *              before threads can pass through {@link #await}
         * @throws IllegalArgumentException if {@code count} is negative
         */
        public ExchangeCountDownLatch(int count) {
            super(count);
        }
    }
}
