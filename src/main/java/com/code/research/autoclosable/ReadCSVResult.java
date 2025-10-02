package com.code.research.autoclosable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record ReadCSVResult<T>(T value, Throwable error, String message) {

    public static <T> ReadCSVResult<T> ok(T value) {
        return new ReadCSVResult<>(value, null, null);
    }

    public static <T> ReadCSVResult<T> err(String message, Throwable error) {
        return new ReadCSVResult<>(null, error, message);
    }

    public boolean isOk() {
        return error == null;
    }

    public Optional<Throwable> errorRead() {
        return Optional.ofNullable(error);
    }

    public Optional<String> messageRead() {
        return Optional.ofNullable(message);
    }

    public T orElse(T fallback) {
        return isOk() ? value : fallback;
    }

    public T orElseGet(Supplier<? extends T>  supplier) {
        return isOk() ? value : supplier.get();
    }

    public <U> ReadCSVResult<U> map(Function<? super T, ? extends U> f){
        return isOk() ? ok(f.apply(value)) : err(message, error);
    }
}
