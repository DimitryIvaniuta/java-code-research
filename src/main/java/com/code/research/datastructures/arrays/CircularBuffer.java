package com.code.research.datastructures.arrays;

import com.code.research.exceptions.EmptyBufferException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CircularBuffer {

    private final int[] buffer;
    private int head;
    private int tail;
    private int size;

    public CircularBuffer(int capacity) {
        buffer = new int[capacity];
        head = 0;
        tail = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == buffer.length;
    }

    public void add(int value) {
        if (isFull()) {
            // Overwrite the oldest value and move head pointer
            log.info("Buffer is full. Overwriting oldest value.");
            buffer[tail] = value;
            log.info("head: {}:{} ", head, ((head + 1) % buffer.length));

            head = (head + 1) % buffer.length;
        } else {
            buffer[tail] = value;
            size++;
        }
        log.info("tail: {}:{} ", tail, ((tail + 1) % buffer.length));
        tail = (tail + 1) % buffer.length;
        log.info("head: {}, tail: {}, size: {}", head, tail, size);
    }

    public int remove() {
        if (isEmpty()) {
            throw new EmptyBufferException("Buffer is empty");
        }
        int value = buffer[head];
        head = (head + 1) % buffer.length;
        size--;
        return value;
    }

    public static void main(String[] args) {
        CircularBuffer cb = new CircularBuffer(3);
        cb.add(10);
        cb.add(20);
        cb.add(30);
        cb.add(31);
        cb.add(32);
        cb.add(33);
        log.info("Removed: {}", cb.remove());
        cb.add(40);

        while (!cb.isEmpty()) {
            log.info("Removed: {}", cb.remove());
        }
    }

}
