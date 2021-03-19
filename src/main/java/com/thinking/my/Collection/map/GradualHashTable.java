package com.thinking.my.Collection.map;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * 渐进式 Hash 表
 * @author jiangxukun
 * @date 2020/7/8
 * @desc
 */
public class GradualHashTable<K, V> {

    private SimpleHashTable[] tables = new SimpleHashTable[2];
    private int rehashIndex = -1;
    private AtomicInteger iterators = new AtomicInteger(0);
    private int count;
    //private int step = 1;
    public List<Long> newTableTime = new ArrayList<>(); // 用于测试，记录rehash时创建大数组的耗时。

    public GradualHashTable() {
        this(16);
    }

    public GradualHashTable(int capacity) {
        tables[0] = new SimpleHashTable(capacity);
    }

    public synchronized V get(K key) {
        if (isRehashing())
            rehashStep(1);
        Entry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    public synchronized V put(K key, V value) {
        if (isRehashing())
            rehashStep(1);

        Entry<K, V> entry = getEntry(key);
        if (entry != null) {
            V oldValue = entry.getValue();
            entry.setValue(value);
            return oldValue;
        }

        if (tables[0].needRehash()) {
            rehash(tables[0].capacity * 2);
        }

        SimpleHashTable table = !isRehashing() ? tables[0] : tables[1];
        putVal(table, key, value);
        table.size ++;
        count ++;
        return null;
    }

    public synchronized V delete(K key) {
        if (isRehashing())
            rehashStep(1);

        int hash = hash(key);

        for (int i = 0; i < 2; i ++) {
            SimpleHashTable table = tables[i];
            int index = hash & table.capacityMask;
            Entry<K, V> head = table.table[index];
            Entry<K, V> next = head;
            Entry<K, V> pre = null;
            while (next != null) {
                if (next.hash == hash && Objects.equals(next.key, key)) {
                    if (pre == null) {
                        table.table[index] = next.next;
                    } else {
                        pre.next = next.next;
                    }

                    next.next = null;
                    table.size --;
                    count --;
                    return next.getValue();
                }
                pre = next;
                next = next.next;
            }
            if (!isRehashing()) break;
        }
        return null;
    }

    public int size() {
        return count;
    }

    private void rehash(int toSize) {
        if (isRehashing()) return;
        long start = System.nanoTime();
        tables[1] = new SimpleHashTable(toSize);
        newTableTime.add(System.nanoTime() - start);
        rehashIndex = 0;
    }

    private void rehashStep(int step) {
        if (iterators.get() != 0) return;

        while (step-- > 0) {
            if (tables[0].size == 0) {
                tables[0] = tables[1];
                tables[1] = null;
                rehashIndex = -1;
                return ;
            }

            SimpleHashTable table = tables[0];
            assert rehashIndex >= table.capacity : "error: out of index";
            while(table.table[rehashIndex] == null) rehashIndex++;

            SimpleHashTable toTable = tables[1];
            Entry<K, V> head = table.table[rehashIndex];
            Entry<K, V> next;
            while(head != null) {
                int index = head.hash & toTable.capacityMask;
                next = head.next;
                head.next = toTable.table[index];
                toTable.table[index] = head;

                head = next;
                table.size --;
                toTable.size ++;
            }
            table.table[rehashIndex] = null;
            rehashIndex ++;
        }
    }

    private boolean isRehashing() {
        return rehashIndex != -1;
    }

    public int hash(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ hash >>> 16;
    }

    private Entry<K, V> getEntry(K key) {
        int hash = hash(key);
        for (int i = 0; i < 2; i ++) {
            SimpleHashTable table = tables[i];
            Entry<K, V> head = table.table[hash & table.capacityMask];
            while (head != null) {
                if (head.hash == hash && Objects.equals(head.key, key)) {
                    return head;
                }
                head = head.next;
            }

            if (!isRehashing()) break;
        }
        return null;
    }

    // 头部插入
    private void putVal(SimpleHashTable table, K key, V val) {
        int hash = hash(key);
        int i = hash & table.capacityMask;
        Entry<K, V> head = table.table[i];
        Entry<K, V> newEntry = new Entry(hash, key, val, head);
        table.table[i] = newEntry;
    }

    public Iterator<Entry<K, V>> iterator(boolean isSafe) {
        return new HashIterator(isSafe);
    }

    private static class SimpleHashTable<K, V> {
        private Entry<K, V>[] table;
        private int capacity;
        private int capacityMask;
        private int threshold;
        private static final int DEFAULT_CAPACITY = 16;
        private static final int MAX_CAPACITY = 1 << 30;

        private int size;

        public SimpleHashTable() {
            this(DEFAULT_CAPACITY);
        }

        public SimpleHashTable(int capacity) {
            if (capacity < DEFAULT_CAPACITY) capacity = DEFAULT_CAPACITY;
            this.capacity = toSize(capacity);
            this.table = new Entry[this.capacity];
            this.capacityMask = this.capacity - 1;
            this.threshold = (int) Math.min(this.capacity * 0.75f, MAX_CAPACITY + 1);
        }

        public int toSize(int size) {
            if (size >= MAX_CAPACITY) return MAX_CAPACITY;
            int bit = Integer.highestOneBit(size - 1);
            return bit << 1;
        }

        public boolean needRehash() {
            return size >= threshold;
        }
    }

    public static class Entry<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Entry<K,V> next;

        public Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key =  key;
            this.value = value;
            this.next = next;
        }

        @SuppressWarnings("unchecked")
        protected Object clone() {
            return new Entry<>(hash, key, value,
                    (next==null ? null : (Entry<K,V>) next.clone()));
        }

        // Map.Entry Ops
        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            if (value == null)
                throw new NullPointerException();

            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;

            return (key==null ? e.getKey()==null : key.equals(e.getKey())) &&
                    (value==null ? e.getValue()==null : value.equals(e.getValue()));
        }

        public int hashCode() {
            return hash ^ Objects.hashCode(value);
        }

        public String toString() {
            return key.toString()+"="+value.toString();
        }
    }

    public class HashIterator<K, V> implements Iterator<Entry<K, V>> {
        Entry<K, V> next;        // next entry to return
        Entry<K, V> current;     // current entry
        int index;
        int table;
        boolean safe;

        HashIterator(boolean safe) {
            this.safe = safe;
            if (safe)
                iterators.incrementAndGet();

            for (int i = 0; i < 2; i++) {
                while (index < tables[table].capacity && (next = tables[table].table[index++]) == null) {}

                if (!isRehashing()) break;
            }
        }

        @Override
        public boolean hasNext() {
            if (next == null) release();
            return next != null;
        }

        @Override
        public Entry<K, V> next() {
            Entry e = next;
            if ((current = next).next != null) {
                next = current.next;
            } else {
                for (int i = table; i < 2; i++) {
                    while (index < tables[table].capacity && (next = tables[table].table[index++]) == null) {}

                    if (!isRehashing()) break;
                }
            }
            return e;
        }

        public void release() {
            if (safe)
                iterators.decrementAndGet();
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super Entry<K, V>> action) {
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(next());
        }
    }

    // 正确性校验
    public static void main(String[] args) {
        int num = 100_0000;
        GradualHashTable<Integer, Integer> gradualHashTable = new GradualHashTable();
        Hashtable<Integer, Integer> hashtable = new Hashtable();

        Random random = new Random();
        for (int i = 0; i < num; i ++) {
            gradualHashTable.put(new Integer(i), new Integer(i));
            hashtable.put(new Integer(i), new Integer(i));

            // 随机更新
            int percent = random.nextInt(10);
            if (percent <= 5 && i > 0) {
                int i1 = random.nextInt(i);
                Integer integer = hashtable.get(i1);
                if (integer == null) continue;
                int res = integer + i1;
                gradualHashTable.put(integer, res);
                hashtable.put(integer, res);
            }

            // 随机删除
            if (percent >= 8 && i > 0) {
                int i1 = random.nextInt(i);
                gradualHashTable.delete(i1);
                hashtable.remove(i1);
            }
        }

        assertEquals( hashtable.size() == gradualHashTable.size(), "size equals");

        for (Map.Entry<Integer, Integer> entry : hashtable.entrySet()) {
            Integer val = gradualHashTable.get(entry.getKey());
            assertEquals(!entry.getValue().equals(val), "not equals, key=" + entry.getKey());
        }

        // iterator 测试
        Iterator<Entry<Integer, Integer>> iterator = gradualHashTable.iterator(true);
        int count = 0;
        while (iterator.hasNext()) {
            Entry<Integer, Integer> next = iterator.next();
            Integer val = hashtable.get(next.getKey());
            assertEquals(!next.getValue().equals(val), "not equals, key=" + next.getKey());
            count ++;
        }
        assertEquals(count == hashtable.size(), "itr size equals");
    }

    private static void assertEquals(boolean bol, String message) {
        if (bol)
            System.out.println(message);
    }
}
