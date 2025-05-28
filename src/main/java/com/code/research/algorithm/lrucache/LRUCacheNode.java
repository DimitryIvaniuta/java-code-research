package com.code.research.algorithm.lrucache;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LRUCacheNode {
    // cache key
    private int key;
    //cache value
    private int value;
    // previous node in usage order
    LRUCacheNode prev;
    // next node in usage order
    LRUCacheNode next;

    public LRUCacheNode(int key, int value) {
        this.key = key;
        this.value = value;
    }

}
