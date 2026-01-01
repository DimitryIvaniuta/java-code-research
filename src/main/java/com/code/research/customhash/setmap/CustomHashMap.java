package com.code.research.customhash.setmap;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * CustomHashMap<K,V> — JDK-HashMap-like design:
 * - power-of-two table sizes
 * - spread hash: h ^ (h >>> 16)
 * - separate chaining (linked lists)
 * - supports null keys
 * - fail-fast iterators
 *
 * Provides map views: keySet(), values(), entrySet()
 */
public final class CustomHashMap<K, V> implements Map<K, V>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;

    /** Structural modification count for fail-fast iterators. */
    transient int modCount;

    // ---- views (created lazily) ----
    private transient KeySet keySet;
    private transient Values values;
    private transient EntrySet entrySet;

    public CustomHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public CustomHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public CustomHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) throw new IllegalArgumentException("initialCapacity < 0");
        if (!(loadFactor > 0.0f) || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("invalid loadFactor: " + loadFactor);
        }
        this.loadFactor = loadFactor;

        int cap = Hashing.tableSizeFor(Math.max(DEFAULT_INITIAL_CAPACITY, initialCapacity), MAXIMUM_CAPACITY);
        this.table = Hashing.newTable(cap);
        this.threshold = Hashing.calcThreshold(cap, loadFactor);
    }

    // --- package-friendly accessors for HashSet clone sizing ---
    int capacity() { return table.length; }
    float loadFactor() { return loadFactor; }

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Node<K,V>[] tab = table;
        for (Node<K,V> head : tab) {
            for (Node<K,V> n = head; n != null; n = n.next) {
                if (Objects.equals(n.value, value)) return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Node<K,V> n = getNode(key);
        return (n == null) ? null : n.value;
    }

    private Node<K,V> getNode(Object key) {
        int h = Hashing.spreadHash(key);
        int idx = Hashing.indexFor(h, table.length);
        for (Node<K,V> n = table[idx]; n != null; n = n.next) {
            if (n.hash == h && Objects.equals(n.key, key)) return n;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        return putVal(key, value, false);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return putVal(key, value, true);
    }

    private V putVal(K key, V value, boolean onlyIfAbsent) {
        int h = Hashing.spreadHash(key);
        int idx = Hashing.indexFor(h, table.length);

        for (Node<K,V> n = table[idx]; n != null; n = n.next) {
            if (n.hash == h && Objects.equals(n.key, key)) {
                V old = n.value;
                if (!onlyIfAbsent) n.value = value;
                return old;
            }
        }

        // insert at head
        table[idx] = new Node<>(h, key, value, table[idx]);
        size++;
        modCount++;

        if (size > threshold) resize();
        return null;
    }

    @Override
    public V remove(Object key) {
        return removeNode(key);
    }

    private V removeNode(Object key) {
        int h = Hashing.spreadHash(key);
        int idx = Hashing.indexFor(h, table.length);

        Node<K,V> prev = null;
        Node<K,V> cur = table[idx];

        while (cur != null) {
            Node<K,V> next = cur.next;
            if (cur.hash == h && Objects.equals(cur.key, key)) {
                if (prev == null) table[idx] = next; else prev.next = next;
                size--;
                modCount++;
                return cur.value;
            }
            prev = cur;
            cur = next;
        }
        return null;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        Arrays.fill(table, null);
        size = 0;
        modCount++;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Objects.requireNonNull(m, "map");
        if (m.isEmpty()) return;
        ensureCapacityFor(size + m.size());
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    void ensureCapacityFor(int expectedSize) {
        if (expectedSize <= threshold) return;

        int cap = table.length;
        int target = cap;
        while (target < MAXIMUM_CAPACITY && expectedSize > (int) (target * loadFactor)) {
            target <<= 1;
        }
        if (target != cap) resizeTo(target);
    }

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
        Node<K,V>[] oldTab = table;
        Node<K,V>[] newTab = Hashing.newTable(newCap);

        for (Node<K,V> head : oldTab) {
            Node<K,V> n = head;
            while (n != null) {
                Node<K,V> next = n.next;
                int idx = Hashing.indexFor(n.hash, newCap);
                n.next = newTab[idx];
                newTab[idx] = n;
                n = next;
            }
        }

        table = newTab;
        threshold = Hashing.calcThreshold(newCap, loadFactor);
        modCount++; // structural change
    }

    // ---- Map views ----

    @Override
    public Set<K> keySet() {
        KeySet ks = keySet;
        return (ks != null) ? ks : (keySet = new KeySet());
    }

    @Override
    public Collection<V> values() {
        Values vs = values;
        return (vs != null) ? vs : (values = new Values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        EntrySet es = entrySet;
        return (es != null) ? es : (entrySet = new EntrySet());
    }

    // ---- Iteration base ----

    abstract class HashIterator<T> implements Iterator<T> {
        int expectedModCount = modCount;
        int bucketIndex = 0;
        Node<K,V> next;
        Node<K,V> lastReturned;

        HashIterator() {
            advanceToNextNonEmptyBucket();
        }

        private void advanceToNextNonEmptyBucket() {
            while (bucketIndex < table.length && (next = table[bucketIndex]) == null) {
                bucketIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        Node<K,V> nextNode() {
            checkForComodification();
            if (next == null) throw new NoSuchElementException();

            Node<K,V> e = next;
            lastReturned = e;

            if (e.next != null) {
                next = e.next;
            } else {
                bucketIndex++;
                next = null;
                advanceToNextNonEmptyBucket();
            }
            return e;
        }

        @Override
        public void remove() {
            checkForComodification();
            if (lastReturned == null) throw new IllegalStateException("next() not called or already removed");
            CustomHashMap.this.remove(lastReturned.key);
            lastReturned = null;
            expectedModCount = modCount;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) throw new ConcurrentModificationException();
        }
    }

    final class KeyIterator extends HashIterator<K> {
        @Override public K next() { return nextNode().key; }
    }

    final class ValueIterator extends HashIterator<V> {
        @Override public V next() { return nextNode().value; }
    }

    final class EntryIterator extends HashIterator<Entry<K,V>> {
        @Override public Entry<K,V> next() { return nextNode(); }
    }

    // ---- Views ----

    final class KeySet extends AbstractSet<K> {
        @Override public int size() { return CustomHashMap.this.size(); }
        @Override public void clear() { CustomHashMap.this.clear(); }
        @Override public boolean contains(Object o) { return CustomHashMap.this.containsKey(o); }
        @Override public boolean remove(Object o) { return CustomHashMap.this.remove(o) != null; }
        @Override public Iterator<K> iterator() { return new KeyIterator(); }

        @Override
        public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c, "collection");
            boolean modified = false;
            Iterator<K> it = iterator();
            while (it.hasNext()) {
                K k = it.next();
                if (!c.contains(k)) {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }
    }

    final class Values extends AbstractCollection<V> {
        @Override public int size() { return CustomHashMap.this.size(); }
        @Override public void clear() { CustomHashMap.this.clear(); }
        @Override public boolean contains(Object o) { return CustomHashMap.this.containsValue(o); }
        @Override public Iterator<V> iterator() { return new ValueIterator(); }
    }

    final class EntrySet extends AbstractSet<Entry<K,V>> {
        @Override public int size() { return CustomHashMap.this.size(); }
        @Override public void clear() { CustomHashMap.this.clear(); }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Entry<?,?> e)) return false;
            Node<K,V> n = getNode(e.getKey());
            return n != null && Objects.equals(n.value, e.getValue());
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Entry<?,?> e)) return false;
            Node<K,V> n = getNode(e.getKey());
            if (n != null && Objects.equals(n.value, e.getValue())) {
                CustomHashMap.this.remove(e.getKey());
                return true;
            }
            return false;
        }

        @Override
        public Iterator<Entry<K,V>> iterator() {
            return new EntryIterator();
        }
    }

    // ---- Other Map methods ----

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V v = get(key);
        return (v != null || containsKey(key)) ? v : defaultValue;
    }

    @Override
    public V replace(K key, V value) {
        Node<K,V> n = getNode(key);
        if (n == null) return null;
        V old = n.value;
        n.value = value;
        return old;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Node<K,V> n = getNode(key);
        if (n == null || !Objects.equals(n.value, oldValue)) return false;
        n.value = newValue;
        return true;
    }

//    @Override
    public V replaceAll(BiConsumer<? super K, ? super V> action) {
        throw new UnsupportedOperationException("Use forEach + put if needed");
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action, "action");
        int expected = modCount;
        for (Node<K,V> head : table) {
            for (Node<K,V> n = head; n != null; n = n.next) {
                action.accept(n.key, n.value);
            }
        }
        if (modCount != expected) throw new ConcurrentModificationException();
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Entry<K,V> e : entrySet()) h += e.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Map<?,?> m)) return false;
        if (m.size() != size) return false;
        try {
            for (Entry<K,V> e : entrySet()) {
                K k = e.getKey();
                V v = e.getValue();
                if (!Objects.equals(v, m.get(k))) return false;
            }
            return true;
        } catch (ClassCastException | NullPointerException ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        Iterator<Entry<K,V>> it = entrySet().iterator();
        if (!it.hasNext()) return "{}";
        StringBuilder sb = new StringBuilder().append('{');
        while (true) {
            Entry<K,V> e = it.next();
            sb.append(e.getKey()).append('=').append(e.getValue());
            if (!it.hasNext()) return sb.append('}').toString();
            sb.append(", ");
        }
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public CustomHashMap<K,V> clone() {
        CustomHashMap<K,V> copy = new CustomHashMap<>(capacity(), loadFactor);
        copy.putAll(this);
        return copy;
    }
}
