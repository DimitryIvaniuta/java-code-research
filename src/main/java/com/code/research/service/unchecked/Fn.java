package com.code.research.service.unchecked;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

/** Utils for checked exceptions in streams. */

public final class Fn {
    private Fn() {}

    @FunctionalInterface
    public interface ThrowingFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    public static <T, R, E extends Exception>
    Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (RuntimeException e) {
                throw e; // keep unchecked as-is
            } catch (Exception e) {
                if (e instanceof IOException ioe) {
                    throw new UncheckedIOException(ioe);     // map I/O specifically
                }
                throw new RuntimeException(e);             // domain-specific wrapper if you prefer
            }
        };
    }
}
