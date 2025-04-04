package com.code.research.tcp;

/**
 * Private class representing a contiguous segment of the stream.
 */
public class Segment {

    final int start;  // Inclusive starting offset.
    final int end;    // Exclusive ending offset.
    final byte[] data;

    Segment(int start, int end, byte[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

}
