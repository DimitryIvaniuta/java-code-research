package com.code.research.service.unchecked;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

/** Utils for checked exceptions in streams. */
final class Fn {
    @FunctionalInterface
    interface ThrowingFunction<T, R, E extends Exception> { R apply(T t) throws E; }

    public static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R, ?> f) {
        return t -> {
            try { return f.apply(t); }
            catch (IOException e) { throw new UncheckedIOException(e); }        // map I/O
            catch (RuntimeException e) { throw e; }
            catch (Exception e) { throw new RuntimeException(e); }              // or custom
        };
    }
}
