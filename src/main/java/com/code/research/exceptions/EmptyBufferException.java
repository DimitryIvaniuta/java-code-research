package com.code.research.exceptions;

public class EmptyBufferException extends RuntimeException {

    public EmptyBufferException() {
        super();
    }

    public EmptyBufferException(final String message) {
        super(message);
    }

    public EmptyBufferException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
