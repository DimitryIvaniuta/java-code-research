package com.code.research.customhash.setmap;

/**
 * Hashing + sizing utilities (JDK HashMap-inspired).
 */
final class Hashing {

    private Hashing() {}

    static int spreadHash(Object key) {
        int h = (key == null) ? 0 : key.hashCode();
        return h ^ (h >>> 16);
    }

    static int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    static int tableSizeFor(int cap, int maxCap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        if (n < 0) return 1;
        if (n >= maxCap) return maxCap;
        return n + 1;
    }

    static int calcThreshold(int capacity, float loadFactor) {
        return (int) Math.min((float) capacity * loadFactor, (float) Integer.MAX_VALUE);
    }

    @SuppressWarnings("unchecked")
    static <K, V> Node<K, V>[] newTable(int capacity) {
        return (Node<K, V>[]) new Node[capacity];
    }
}
