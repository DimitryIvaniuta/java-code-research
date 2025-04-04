package com.code.research.tcp;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * TCPStream simulates a minimal TCP stream.
 *
 * It supports:
 *  - getPacket(int offset, byte[] data): receiving out-of-order packets.
 *  - read(byte[] dest): reading contiguous data starting at the current read pointer.
 *
 * The stream reconstructs the byte stream from packets identified by their offset.
 */
public class TCPStream {

    // Sorted map to store received bytes: key is stream offset, value is the byte.
    private final NavigableMap<Integer, Byte> streamData;
    // The next expected byte offset.
    private int readPointer;

    /**
     * Constructs an empty TCPStream.
     */
    public TCPStream() {
        this.streamData = new TreeMap<>();
        this.readPointer = 0;
    }

    /**
     * Saves the packet data starting at the given offset.
     * Each byte in the packet is stored in the streamData map.
     *
     * @param offset the starting offset of the packet in the stream.
     * @param data   the byte array containing packet data.
     */
    public void getPacket(int offset, byte[] data) {
        if (data == null || data.length == 0) {
            return;
        }
        for (int i = 0; i < data.length; i++) {
            // Overwrite any previously stored byte at the same offset.
            streamData.put(offset + i, data[i]);
        }
    }

    /**
     * Reads contiguous bytes from the current read pointer into the provided destination array.
     * Reading stops at the first missing byte in the stream.
     * If no data is available at the read pointer, returns 0.
     *
     * @param dest the destination byte array to fill with stream data.
     * @return the number of bytes read into dest, or 0 if no data is available.
     */
    public int read(byte[] dest) {
        if (dest == null || dest.length == 0) {
            return 0;
        }
        int bytesRead = 0;
        int currentPos = readPointer;
        // Temporary buffer for contiguous bytes
        byte[] buffer = new byte[dest.length];

        while (bytesRead < dest.length && streamData.containsKey(currentPos)) {
            buffer[bytesRead] = streamData.get(currentPos);
            bytesRead++;
            currentPos++;
        }

        // If no contiguous data is available at the read pointer, return 0.
        if (bytesRead == 0) {
            return 0;
        }

        // Copy the contiguous data into dest.
        System.arraycopy(buffer, 0, dest, 0, bytesRead);
        // Update the read pointer to the new position.
        readPointer = currentPos;
        return bytesRead;
    }

}
