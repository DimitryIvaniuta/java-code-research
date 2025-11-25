package com.code.research.algorithm.test;

// Adjust the package if your class is in a package.
import com.code.research.algorithm.FindDuplicate287;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FindDuplicate287Test {

    // Helper: call and assert result equals expected, and array not modified
    private void assertDuplicate(int expected, int[] nums) {
        int[] copy = nums.clone();                 // keep original snapshot
        int got = FindDuplicate287.findDuplicate(nums);
        assertEquals(expected, got);               // correct duplicate?
        assertArrayEquals(copy, nums);             // algorithm must NOT modify input
    }

    @Test
    void examples_from_prompt() {
        assertDuplicate(2, new int[]{1,3,4,2,2});
        assertDuplicate(3, new int[]{3,1,3,4,2});
        assertDuplicate(3, new int[]{3,3,3,3,3});
    }

    @Test
    void duplicate_is_1() {
        // n = 5, values in [1..5], duplicate = 1
        assertDuplicate(1, new int[]{1,4,2,1,3,5});
    }

    @Test
    void duplicate_is_n() {
        // n = 6, values in [1..6], duplicate = 6
        assertDuplicate(6, new int[]{2,6,4,1,3,5,6});
    }

    @Test
    void minimal_valid_size() {
        // n+1 = 2, numbers in [1..1], only possible duplicate is 1
        assertDuplicate(1, new int[]{1,1});
    }

    @Test
    void larger_case_multiple_occurrences() {
        // One number repeats multiple times; still must return that number.
        assertDuplicate(7, new int[]{7,5,4,3,2,1,7,6,7});
    }
}
