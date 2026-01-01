package com.code.research.customhash.set;

/**
 * Hashing + table sizing helpers.
 * Mirrors classic HashMap ideas:
 * - power-of-two table sizes
 * - spread hash bits: h ^ (h >>> 16)
 */
final class HashUtils {

    private HashUtils() {}

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

        int res = (n < 0) ? 1 : (n >= maxCap) ? maxCap : n + 1;
        return res;
    }

    static int calcThreshold(int capacity, float loadFactor) {
        return (int) Math.min((float) capacity * loadFactor, (float) Integer.MAX_VALUE);
    }

    @SuppressWarnings("unchecked")
    static <E> HashNode<E>[] newTable(int capacity) {
        return (HashNode<E>[]) new HashNode[capacity];
    }
}
