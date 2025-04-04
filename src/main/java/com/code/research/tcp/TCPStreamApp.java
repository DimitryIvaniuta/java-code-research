package com.code.research.tcp;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TCPStreamApp {

    /**
     * Example usage demonstrating out-of-order packet reception and stream reading.
     */
    public static void main(String[] args) {
        TCPStream stream = new TCPStream();

        // Simulate packets arriving out-of-order.
        stream.getPacket(5, "World".getBytes());  // packet starting at offset 5
        stream.getPacket(0, "Hello".getBytes());    // packet starting at offset 0

        // Destination buffer with capacity for 10 bytes.
        byte[] dest = new byte[10];
        int n = stream.read(dest);
        if (n > 0) {
            log.info("Read: {}", new String(Arrays.copyOf(dest, n)));
        } else {
            log.info("No data available at current pointer.");
        }

        // Further demonstration: reading again when no data is available.
        n = stream.read(dest);
        if (n > 0) {
            log.info("Additional read: {}", new String(Arrays.copyOf(dest, n)));
        } else {
            log.info("No additional data available at current pointer.");
        }
    }

}
