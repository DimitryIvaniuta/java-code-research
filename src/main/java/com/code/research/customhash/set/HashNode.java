package com.code.research.customhash.set;


/**
 * Single node in a bucket chain (separate chaining).
 *
 * - hash: cached "spread" hash for faster compares and stable rehashing
 * - key: stored set element
 * - next: link to next node in same bucket
 */
final class HashNode<E> {
    final int hash;
    final E key;
    HashNode<E> next;

    HashNode(int hash, E key, HashNode<E> next) {
        this.hash = hash;
        this.key = key;
        this.next = next;
    }
}
