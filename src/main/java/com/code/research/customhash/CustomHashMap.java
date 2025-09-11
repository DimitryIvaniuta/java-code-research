package com.code.research.customhash;

public class CustomHashMap<K, V> implements CustomMap<K, V> {

    /**
     * Default initial capacity (power of two for bit-masking).
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 16 = (2^4)

    /**
     * Maximum capacity (to avoid huge allocations).
     */
    static final int MAXIMUM_CAPACITY = 1 << 30; //1 073 741 824 = (2^30)

    /**
     * Default load factor; controls resizing threshold.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Array of buckets (linked lists of Nodes).
     */
    Node<K, V>[] table;

    /**
     * Number of key-value mappings stored.
     */
    int size;

    /**
     * Next size threshold to trigger a resize.
     */
    int threshold;

    /**
     * Load factor for resizing.
     */
    final float loadFactor;

    /**
     * Constructs a new, empty map with default capacity and load factor.
     */
    public CustomHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.threshold = DEFAULT_INITIAL_CAPACITY;
        this.table = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * Calculates supplemental hash to spread bits, reducing collisions.
     */
    static int hash(Object key) {
        int h;
        // Apply key.hashCode(), then xor-shift high bits for better distribution
        return key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * Returns index for hash within table length (power of two) via bitmask.
     */
    static int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    @Override
    public V put(K key, V value) {
// Compute hash and target bucket index
        int h = hash(key);
        int i = indexFor(h, table.length);
// Traverse bucket: if key exists, replace value
        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            if (e.hash == h && (e.key == key || (key != null && key.equals(e.key)))) {
                V old = e.value;
                e.value = value;
                return old; // return previous value
            }
        }
// Key not found: add new node at bucket head
        Node<K, V> newNode = new Node<>(h, key, value, table[i]);
        table[i] = newNode;
        size++;
// Resize if exceeding threshold
        if (size > threshold * loadFactor) {
            resize(2 * table.length);
        }
        return null;
    }

    /**
     * Resizes the table to new capacity and rehashes all entries.
     */
    @SuppressWarnings("unchecked")
    void resize(int newCapacity) {
// Avoid exceeding maximum capacity
        if (newCapacity > MAXIMUM_CAPACITY) newCapacity = MAXIMUM_CAPACITY;
        Node<K, V>[] oldTable = table;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
// Rehash all nodes into new table
        for (Node<K, V> e : oldTable) {
            while (e != null) {
                Node<K, V> next = e.next; // save next
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i]; // insert at head of new bucket
                newTable[i] = e;
                e = next; // proceed to next in old bucket
            }
        }
        table = newTable;
// Update threshold based on load factor
        threshold = (int) (newCapacity * loadFactor);
    }

    @Override
    public V get(K key) {
        int h = hash(key);
        int i = indexFor(h, table.length);
// Traverse bucket for key match
        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            if (e.hash == h && (e.key == key || (key != null && key.equals(e.key)))) {
                return e.value;
            }
        }
        return null; // not found
    }

    @Override
    public V remove(K key) {
        int h = hash(key);
        int i = indexFor(h, table.length);
        Node<K, V> prev = null;
        for (Node<K, V> e = table[i]; e != null; prev = e, e = e.next) {
            if (e.hash == h && (e.key == key || (key != null && key.equals(e.key)))) {
                if (prev == null) {
                    table[i] = e.next; // remove head
                } else {
                    prev.next = e.next; // bypass current
                }
                size--;
                return e.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}
