//package com.thinking.my.concurrent.map;
//
//import javax.swing.text.Segment;
//import java.io.ObjectStreamField;
//import java.io.Serializable;
//import java.util.AbstractMap;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * Created by liyong on 2019/5/24.
// */
//public class MyCurrentHashMap<K,V>  extends AbstractMap<K,V>
//        implements ConcurrentMap<K,V>, Serializable {
//
//
//    /* ---------------- Constants -------------- */
//
//    /**
//     * The largest possible table capacity.  This value must be
//     * exactly 1<<30 to stay within Java array allocation and indexing
//     * bounds for power of two table sizes, and is further required
//     * because the top two bits of 32bit hash fields are used for
//     * control purposes.
//     */
//    private static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    /**
//     * The default initial table capacity.  Must be a power of 2
//     * (i.e., at least 1) and at most MAXIMUM_CAPACITY.
//     */
//    private static final int DEFAULT_CAPACITY = 16;
//
//    /**
//     * The largest possible (non-power of two) array size.
//     * Needed by toArray and related methods.
//     */
//    static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
//
//    /**
//     * The default concurrency level for this table. Unused but
//     * defined for compatibility with previous versions of this class.
//     */
//    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
//
//    /**
//     * The load factor for this table. Overrides of this value in
//     * constructors affect only the initial table capacity.  The
//     * actual floating point value isn't normally used -- it is
//     * simpler to use expressions such as {@code n - (n >>> 2)} for
//     * the associated resizing threshold.
//     */
//    private static final float LOAD_FACTOR = 0.75f;
//
//    /**
//     * The bin count threshold for using a tree rather than list for a
//     * bin.  Bins are converted to trees when adding an element to a
//     * bin with at least this many nodes. The value must be greater
//     * than 2, and should be at least 8 to mesh with assumptions in
//     * tree removal about conversion back to plain bins upon
//     * shrinkage.
//     */
//    static final int TREEIFY_THRESHOLD = 8;
//
//    /**
//     * The bin count threshold for untreeifying a (split) bin during a
//     * resize operation. Should be less than TREEIFY_THRESHOLD, and at
//     * most 6 to mesh with shrinkage detection under removal.
//     */
//    static final int UNTREEIFY_THRESHOLD = 6;
//
//    /**
//     * The smallest table capacity for which bins may be treeified.
//     * (Otherwise the table is resized if too many nodes in a bin.)
//     * The value should be at least 4 * TREEIFY_THRESHOLD to avoid
//     * conflicts between resizing and treeification thresholds.
//     */
//    static final int MIN_TREEIFY_CAPACITY = 64;
//
//    /**
//     * Minimum number of rebinnings per transfer step. Ranges are
//     * subdivided to allow multiple resizer threads.  This value
//     * serves as a lower bound to avoid resizers encountering
//     * excessive memory contention.  The value should be at least
//     * DEFAULT_CAPACITY.
//     */
//    private static final int MIN_TRANSFER_STRIDE = 16;
//
//    /**
//     * The number of bits used for generation stamp in sizeCtl.
//     * Must be at least 6 for 32bit arrays.
//     */
//    private static int RESIZE_STAMP_BITS = 16;
//
//    /**
//     * The maximum number of threads that can help resize.
//     * Must fit in 32 - RESIZE_STAMP_BITS bits.
//     */
//    private static final int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;
//
//    /**
//     * The bit shift for recording size stamp in sizeCtl.
//     */
//    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
//
//    /*
//     * Encodings for Node hash fields. See above for explanation.
//     */
//    static final int MOVED     = -1; // hash for forwarding nodes
//    static final int TREEBIN   = -2; // hash for roots of trees
//    static final int RESERVED  = -3; // hash for transient reservations
//    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
//
//    /** Number of CPUS, to place bounds on some sizings */
//    static final int NCPU = Runtime.getRuntime().availableProcessors();
//
//    /** For serialization compatibility. */
//    private static final ObjectStreamField[] serialPersistentFields = {
//            new ObjectStreamField("segments", Segment[].class),
//            new ObjectStreamField("segmentMask", Integer.TYPE),
//            new ObjectStreamField("segmentShift", Integer.TYPE)
//    };
//
//
//    /**
//     * Maps the specified key to the specified value in this table.
//     * Neither the key nor the value can be null.
//     *
//     * <p>The value can be retrieved by calling the {@code get} method
//     * with a key that is equal to the original key.
//     *
//     * @param key key with which the specified value is to be associated
//     * @param value value to be associated with the specified key
//     * @return the previous value associated with {@code key}, or
//     *         {@code null} if there was no mapping for {@code key}
//     * @throws NullPointerException if the specified key or value is null
//     */
//    public V put(K key, V value) {
//        return putVal(key, value, false);
//    }
//
//
//    /** Implementation for put and putIfAbsent */
//    final V putVal(K key, V value, boolean onlyIfAbsent) {
//        if (key == null || value == null) throw new NullPointerException();
//        int hash = spread(key.hashCode());
//        int binCount = 0;
//        for (Node<K,V>[] tab = table;;) {
//            Node<K,V> f; int n, i, fh;
//            if (tab == null || (n = tab.length) == 0)
//                tab = initTable();
//            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
//                if (casTabAt(tab, i, null,
//                        new Node<K,V>(hash, key, value, null)))
//                    break;                   // no lock when adding to empty bin
//            }
//            else if ((fh = f.hash) == MOVED)
//                tab = helpTransfer(tab, f);
//            else {
//                V oldVal = null;
//                synchronized (f) {
//                    if (tabAt(tab, i) == f) {
//                        if (fh >= 0) {
//                            binCount = 1;
//                            for (Node<K,V> e = f;; ++binCount) {
//                                K ek;
//                                if (e.hash == hash &&
//                                        ((ek = e.key) == key ||
//                                                (ek != null && key.equals(ek)))) {
//                                    oldVal = e.val;
//                                    if (!onlyIfAbsent)
//                                        e.val = value;
//                                    break;
//                                }
//                                Node<K,V> pred = e;
//                                if ((e = e.next) == null) {
//                                    pred.next = new Node<K,V>(hash, key,
//                                            value, null);
//                                    break;
//                                }
//                            }
//                        }
//                        else if (f instanceof ConcurrentHashMap.TreeBin) {
//                            Node<K,V> p;
//                            binCount = 2;
//                            if ((p = ((ConcurrentHashMap.TreeBin<K,V>)f).putTreeVal(hash, key,
//                                    value)) != null) {
//                                oldVal = p.val;
//                                if (!onlyIfAbsent)
//                                    p.val = value;
//                            }
//                        }
//                    }
//                }
//                if (binCount != 0) {
//                    if (binCount >= TREEIFY_THRESHOLD)
//                        treeifyBin(tab, i);
//                    if (oldVal != null)
//                        return oldVal;
//                    break;
//                }
//            }
//        }
//        addCount(1L, binCount);
//        return null;
//    }
//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        return null;
//    }
//
//    @Override
//    public boolean remove(Object key, Object value) {
//        return false;
//    }
//
//    @Override
//    public boolean replace(K key, V oldValue, V newValue) {
//        return false;
//    }
//}
