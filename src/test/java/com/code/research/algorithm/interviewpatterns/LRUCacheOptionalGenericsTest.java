package com.code.research.algorithm.interviewpatterns;

import com.code.research.interviewpatterns.LRUCacheGenerics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LRUCacheOptionalGenericsTest {

    @Test
    void getOptionalWorks() {
        LRUCacheGenerics<String, Integer> c = new LRUCacheGenerics<>(2);
        assertTrue(c.getOptional("x").isEmpty());

        c.put("x", 10);
        assertEquals(10, c.getOptional("x").orElseThrow());
    }

    @Test
    void removeWorks() {
        LRUCacheGenerics<String, String> c = new LRUCacheGenerics<>(2);
        c.put("a", "A");
        assertEquals("A", c.remove("a").orElseThrow());
        assertTrue(c.getOptional("a").isEmpty());
        assertTrue(c.remove("a").isEmpty());
    }
}
