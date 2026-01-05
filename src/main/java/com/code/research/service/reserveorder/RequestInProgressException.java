package com.code.research.service.reserveorder;

/**
 * Map to 409 in controller advice
 */
public class RequestInProgressException extends RuntimeException {
    public RequestInProgressException(String key) {
        super("Request with this Idempotency-Key is in progress: " + key);
    }
}
