package com.thinking.my.Collection.queue;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @Description 自己实现优先级队列
 * @Author liyong
 * @Date 2020/5/8 7:31 下午
 **/
public class MyPriorityQueue<E> extends AbstractQueue<E> implements Serializable {
    /**
     * 默认大小
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * 优先队列表示为平衡的二进制堆：
     *   queue [n]的两个子是queue [2 * n + 1]和queue [2 *（n + 1）]
     *    优先级队列由比较器或元素的顺序排序
     *    自然排序（如果比较器为null）：对于
     *   堆和每个后代d为n，n <= d。具有元素
     *  最小值是在queue [0]中，假设队列是非空的。
     */
    //非私有以简化嵌套类访问
    //transient关键字的作用，简单地说，就是让某些被修饰的成员属性变量不被序列化
    transient Object[] queue;

    /**
     * The number of elements in the priority queue.
     */
    private int size = 0;
    /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    private final Comparator<? super E> comparator;
    /**
     *优先级队列 结构修改的次数
     */
    transient int modCount = 0;


    public boolean add(E e) {
        return offer(e);
    }

    /**
     * 默认构造
     */
    public MyPriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    /**
     *
     * @param initialCapacity
     * @param comparator
     */
    public MyPriorityQueue(int initialCapacity,
                           Comparator<? super E> comparator) {
        // Note: This restriction of at least one is not actually needed,
        // but continues for 1.5 compatibility
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.queue = new Object[initialCapacity];
        this.comparator = comparator;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        modCount++;
        int i = size;
        if (i >= queue.length)
            grow(i + 1);
        size = i + 1;
        if (i == 0)
            queue[0] = e;
        else
            siftUp(i, e);
        return true;
    }

    /**
     * 在k 位置 插入 元素 x
     * @param k
     * @param x
     */
    private void siftUp(int k, E x) {
        if (comparator != null)
            siftUpUsingComparator(k, x);
        else
            siftUpComparable(k, x);
    }

    private void siftUpComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>) x;
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (key.compareTo((E) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = key;
    }

    private void siftUpUsingComparator(int k, E x) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (comparator.compare(x, (E) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = x;
    }

    public static void main(String[] args) {
       MyPriorityQueue<Integer> queue = new MyPriorityQueue();
       queue.add(2);
       queue.add(5);
       queue.add(4);
    }


    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        // Double size if small; else grow by 50%
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                (oldCapacity + 2) :
                (oldCapacity >> 1));
        // overflow-conscious code
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        queue = Arrays.copyOf(queue, newCapacity);
    }


    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}
