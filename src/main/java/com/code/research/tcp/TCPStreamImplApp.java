package com.code.research.tcp;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TCPStreamImplApp {

    /**
     * A demonstration of usage.
     */
    public static void main(String[] args) {
        TCPStream stream = new TCPStream();

        // Simulate out-of-order packet arrivals.
        stream.getPacket(5, "World".getBytes());
        stream.getPacket(0, "Hello".getBytes());

        // Read the complete stream.
        byte[] buffer = new byte[10];
        int bytesRead = stream.read(buffer);
        if (bytesRead > 0) {
            log.info("Read: {}", new String(Arrays.copyOf(buffer, bytesRead)));
        } else {
            log.info("No data available at the current read pointer.");
        }

        // Demonstrate further reads when no data is available.
        bytesRead = stream.read(buffer);
        if (bytesRead > 0) {
            log.info("Additional read: " + new String(Arrays.copyOf(buffer, bytesRead)));
        } else {
            log.info("No additional data available at the current read pointer.");
        }

        // Simulate a packet that fills an in-gap data.
        // For example, if we had a gap between segments, then fill it.
        stream.getPacket(10, "!!".getBytes());
        // Append data to the stream.
        bytesRead = stream.read(buffer);
        if (bytesRead > 0) {
            log.info("After gap fill read: {}", new String(Arrays.copyOf(buffer, bytesRead)));
        } else {
            log.info("No data available after gap fill.");
        }
    }
}

