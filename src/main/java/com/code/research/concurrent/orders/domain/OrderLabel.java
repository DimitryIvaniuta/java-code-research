package com.code.research.concurrent.orders.domain;

import java.util.Objects;
import java.time.Instant;

/**
 * Represents a shipping label, identified by a tracking number and timestamp.
 */
public final class OrderLabel {
    private final String trackingNumber;
    private final Instant createdAt;

    /**
     * @param trackingNumber the carrier-provided tracking number, non-null and non-empty.
     */
    public OrderLabel(String trackingNumber) {
        this.trackingNumber = Objects.requireNonNull(trackingNumber, "trackingNumber");
        if (trackingNumber.isBlank()) {
            throw new IllegalArgumentException("trackingNumber must not be blank");
        }
        this.createdAt = Instant.now();
    }

    /**
     * Gets the tracking number.
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * Gets the timestamp when the label was created.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLabel orderLabel)) return false;
        return trackingNumber.equals(orderLabel.trackingNumber)
                && createdAt.equals(orderLabel.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber, createdAt);
    }

    @Override
    public String toString() {
        return "Label{trackingNumber='" + trackingNumber + "', createdAt=" + createdAt + "}";
    }
}
