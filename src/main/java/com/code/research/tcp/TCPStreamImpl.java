package com.code.research.tcp;

import java.util.Arrays;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TCPStreamImpl {

    // A TreeMap holding segments, keyed by the segment's starting offset.
    private final NavigableMap<Integer, Segment> segments;
    // The current read pointer in the stream.
    private int readPointer;

    /**
     * Constructs an empty TCPStream.
     */
    public TCPStreamImpl() {
        this.segments = new TreeMap<>();
        this.readPointer = 0;
    }

    /**
     * Saves packet data starting at the given offset.
     * Packets that overlap or are immediately adjacent are merged.
     *
     * @param offset the offset in the stream where this packet's data begins.
     * @param data   the payload of the packet.
     */
    public synchronized void getPacket(int offset, byte[] data) {
        if (data == null || data.length == 0) {
            return;
        }

        // Create a new segment for this packet.
        int newStart = offset;
        int newEnd = offset + data.length;
        byte[] newData = Arrays.copyOf(data, data.length);
        Segment newSeg = new Segment(newStart, newEnd, newData);

        // Check if the packet overlaps or is contiguous with a segment before newSeg.
        Map.Entry<Integer, Segment> lowerEntry = segments.floorEntry(newStart);
        if (lowerEntry != null) {
            Segment lower = lowerEntry.getValue();
            // Merge if overlapping or adjacent.
            if (lower.end >= newStart) { // lower.end == newStart is contiguous.
                newSeg = mergeSegments(lower, newSeg);
                segments.remove(lower.start);
            }
        }

        // Merge any segments that start before or at newSeg.end (i.e. overlapping or contiguous).
        while (true) {
            Map.Entry<Integer, Segment> higherEntry = segments.ceilingEntry(newSeg.start);
            if (higherEntry == null) {
                break;
            }
            Segment higher = higherEntry.getValue();
            // If there is a gap between newSeg and higher, stop merging.
            if (higher.start > newSeg.end) {
                break;
            }
            // Merge with the higher segment.
            newSeg = mergeSegments(newSeg, higher);
            segments.remove(higher.start);
        }

        // Insert the (possibly merged) segment back into the map.
        segments.put(newSeg.start, newSeg);
    }

    /**
     * Reads contiguous bytes from the current read pointer into dest.
     * If no data is available at the read pointer, returns 0.
     * Otherwise, copies as many contiguous bytes as possible (up to dest.length)
     * and advances the read pointer.
     *
     * @param dest the destination buffer.
     * @return the number of bytes read, or 0 if no contiguous data is available.
     */
    public synchronized int read(byte[] dest) {
        if (dest == null || dest.length == 0) {
            return 0;
        }

        // Locate the segment that might cover the read pointer.
        Map.Entry<Integer, Segment> entry = segments.floorEntry(readPointer);
        if (entry == null || entry.getValue().end <= readPointer) {
            // No segment covers the read pointer.
            return 0;
        }
        Segment seg = entry.getValue();
        if (readPointer < seg.start || readPointer >= seg.end) {
            return 0;
        }

        // We now copy contiguous data from segments.
        int totalCopied = 0;
        int currentPos = readPointer;
        byte[] buffer = new byte[dest.length];

        // Iterate over segments as long as they are contiguous.
        while (totalCopied < dest.length) {
            // Get the segment that should cover currentPos.
            Map.Entry<Integer, Segment> currentEntry = segments.floorEntry(currentPos);
            if (currentEntry == null) {
                break;
            }
            Segment currentSeg = currentEntry.getValue();
            // If the current segment does not cover the currentPos, there's a gap.
            if (currentPos < currentSeg.start || currentPos >= currentSeg.end) {
                break;
            }
            // Copy from the current segment.
            int offsetInSegment = currentPos - currentSeg.start;
            int availableInSegment = currentSeg.end - currentPos;
            int toCopy = Math.min(availableInSegment, dest.length - totalCopied);
            System.arraycopy(currentSeg.data, offsetInSegment, buffer, totalCopied, toCopy);
            totalCopied += toCopy;
            currentPos += toCopy;
            // Check if the next segment is immediately contiguous.
            Map.Entry<Integer, Segment> nextEntry = segments.higherEntry(currentSeg.start);
            if (nextEntry == null || nextEntry.getValue().start != currentPos) {
                break;
            }
        }

        if (totalCopied == 0) {
            return 0;
        }

        // Copy the contiguous bytes into dest.
        System.arraycopy(buffer, 0, dest, 0, totalCopied);
        // Advance the read pointer.
        readPointer = currentPos;

        // Clean up segments that have been fully consumed.
        trimSegments();

        return totalCopied;
    }

    /**
     * Removes fully consumed segments and trims partially consumed segments.
     */
    private void trimSegments() {
        // Remove segments that end at or before the current read pointer.
        while (!segments.isEmpty()) {
            Map.Entry<Integer, Segment> firstEntry = segments.firstEntry();
            Segment seg = firstEntry.getValue();
            if (seg.end <= readPointer) {
                segments.pollFirstEntry();
            } else if (seg.start < readPointer) {
                // Partially consumed segment: trim its data.
                int consumed = readPointer - seg.start;
                int remaining = seg.end - readPointer;
                byte[] newData = new byte[remaining];
                System.arraycopy(seg.data, consumed, newData, 0, remaining);
                segments.pollFirstEntry();
                Segment trimmed = new Segment(readPointer, seg.end, newData);
                segments.put(trimmed.start, trimmed);
                break;
            } else {
                break;
            }
        }
    }

    /**
     * Merges two segments that are overlapping or contiguous.
     * The resulting segment covers from min(a.start, b.start) to max(a.end, b.end).
     *
     * @param a the first segment.
     * @param b the second segment.
     * @return a new merged segment.
     */
    private Segment mergeSegments(Segment a, Segment b) {
        int mergedStart = Math.min(a.start, b.start);
        int mergedEnd = Math.max(a.end, b.end);
        int mergedLength = mergedEnd - mergedStart;
        byte[] mergedData = new byte[mergedLength];

        // Copy data from segment a.
        System.arraycopy(a.data, 0, mergedData, a.start - mergedStart, a.end - a.start);
        // Copy data from segment b.
        System.arraycopy(b.data, 0, mergedData, b.start - mergedStart, b.end - b.start);

        return new Segment(mergedStart, mergedEnd, mergedData);
    }

}

