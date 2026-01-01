package com.code.research.customhash.set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Internal hash table specialized for "set" semantics (stores keys only).
 * Uses separate chaining via HashNode.
 *
 * Responsibilities:
 * - Store elements in buckets
 * - Resize / rehash
 * - Track modCount for fail-fast iterators
 */
final class CustomHashTable<E> implements Iterable<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16; // power of 2
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private HashNode<E>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;
    private int modCount;

    CustomHashTable() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    CustomHashTable(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    CustomHashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) throw new IllegalArgumentException("initialCapacity < 0");
        if (!(loadFactor > 0.0f) || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("invalid loadFactor: " + loadFactor);
        }
        this.loadFactor = loadFactor;

        int cap = HashUtils.tableSizeFor(Math.max(DEFAULT_INITIAL_CAPACITY, initialCapacity), MAXIMUM_CAPACITY);
        this.table = HashUtils.newTable(cap);
        this.threshold = HashUtils.calcThreshold(cap, loadFactor);
    }

    int size() {
        return size;
    }

    int capacity() {
        return table.length;
    }

    float loadFactor() {
        return loadFactor;
    }

    boolean contains(Object key) {
        int h = HashUtils.spreadHash(key);
        int idx = HashUtils.indexFor(h, table.length);
        for (HashNode<E> n = table[idx]; n != null; n = n.next) {
            if (n.hash == h && Objects.equals(n.key, key)) return true;
        }
        return false;
    }

    boolean add(E key) {
        int h = HashUtils.spreadHash(key);
        int idx = HashUtils.indexFor(h, table.length);

        for (HashNode<E> n = table[idx]; n != null; n = n.next) {
            if (n.hash == h && Objects.equals(n.key, key)) return false; // already present
        }

        table[idx] = new HashNode<>(h, key, table[idx]); // insert at head
        size++;
        modCount++;

        if (size > threshold) resize();
        return true;
    }

    boolean remove(Object key) {
        int h = HashUtils.spreadHash(key);
        int idx = HashUtils.indexFor(h, table.length);

        HashNode<E> prev = null;
        HashNode<E> cur = table[idx];

        while (cur != null) {
            HashNode<E> next = cur.next;
            if (cur.hash == h && Objects.equals(cur.key, key)) {
                if (prev == null) table[idx] = next; else prev.next = next;
                size--;
                modCount++;
                return true;
            }
            prev = cur;
            cur = next;
        }
        return false;
    }

    void clear() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) table[i] = null;
        size = 0;
        modCount++;
    }

    /**
     * Pre-size to reduce resizing when bulk adding.
     */
    void ensureCapacityFor(int expectedSize) {
        if (expectedSize <= threshold) return;

        int cap = table.length;
        int target = cap;
        while (target < MAXIMUM_CAPACITY && expectedSize > (int) (target * loadFactor)) {
            target <<= 1;
        }
        if (target != cap) resizeTo(target);
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomHashTableIterator<>(this);
    }

    // ---------- Internal resizing ----------

    private void resize() {
        int oldCap = table.length;
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        int newCap = oldCap << 1;
        if (newCap <= 0 || newCap > MAXIMUM_CAPACITY) newCap = MAXIMUM_CAPACITY;
        resizeTo(newCap);
    }

    private void resizeTo(int newCap) {
        HashNode<E>[] oldTab = table;
        HashNode<E>[] newTab = HashUtils.newTable(newCap);

        for (int i = 0; i < oldTab.length; i++) {
            HashNode<E> e = oldTab[i];
            while (e != null) {
                HashNode<E> next = e.next;
                int idx = HashUtils.indexFor(e.hash, newCap);
                e.next = newTab[idx];
                newTab[idx] = e;
                e = next;
            }
        }

        table = newTab;
        threshold = HashUtils.calcThreshold(newCap, loadFactor);
        modCount++; // structural change visible to iterators
    }

    // ---------- Iterator support (package-private access) ----------

    HashNode<E>[] tableRef() {
        return table;
    }

    int modCount() {
        return modCount;
    }

    // ---------- Fail-fast iterator implementation (kept separate class) ----------

    static final class CustomHashTableIterator<E> implements Iterator<E> {
        private final CustomHashTable<E> owner;
        private final HashNode<E>[] tab;

        private int expectedModCount;
        private int bucketIndex;
        private HashNode<E> next;
        private E lastReturned;

        CustomHashTableIterator(CustomHashTable<E> owner) {
            this.owner = owner;
            this.tab = owner.tableRef();
            this.expectedModCount = owner.modCount();
            this.bucketIndex = 0;
            this.next = null;
            advanceToNext();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            checkForComodification();
            if (next == null) throw new NoSuchElementException();

            HashNode<E> n = next;
            lastReturned = n.key;

            // move forward
            if (n.next != null) {
                next = n.next;
            } else {
                bucketIndex++;
                next = null;
                advanceToNext();
            }

            return n.key;
        }

        @Override
        public void remove() {
            checkForComodification();
            if (lastReturned == null) throw new IllegalStateException("next() not called or already removed");

            boolean removed = owner.remove(lastReturned);
            if (!removed) throw new ConcurrentModificationException("Element already removed");

            lastReturned = null;
            expectedModCount = owner.modCount();
        }

        private void advanceToNext() {
            while (bucketIndex < tab.length && (next = tab[bucketIndex]) == null) {
                bucketIndex++;
            }
        }

        private void checkForComodification() {
            if (owner.modCount() != expectedModCount) throw new ConcurrentModificationException();
        }
    }
}
