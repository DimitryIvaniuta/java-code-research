package com.code.research.concurrent.orders.exception;

/**
 * Signals a recoverable error during packing (e.g. out-of-stock).
 */
public class PackingException extends Exception {
    public PackingException(String message) {
        super(message);
    }
    public PackingException(String message, Throwable cause) {
        super(message, cause);
    }
}
