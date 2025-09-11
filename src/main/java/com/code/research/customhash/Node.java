package com.code.research.customhash;

final class Node<K, V> {
    final int hash; // cached hash code of the key
    final K key; // key object
    V value; // associated value
    Node<K, V> next; // next node in the same bucket

    public Node(int hash, K key, V value, Node<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
