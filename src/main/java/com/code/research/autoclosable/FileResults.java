package com.code.research.autoclosable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class FileResults {

    private FileResults() {
        //
    }

    public static <T> FileResult<T> ok(T content) {
        return new OK<>(content);
    }

    public static <T> FileResult<T> err(String message, Throwable error) {
        return new Err<>(
                Objects.requireNonNullElse(message, error.getMessage()),
                Objects.requireNonNull(error, error.getLocalizedMessage()));
    }

    static final class OK<T> implements FileResult<T> {
        private final T content;
        private OK(T content) {
            this.content = content;
        }

        @Override
        public boolean ok() {
            return true;
        }

        @Override
        public Optional<T> value() {
            return Optional.ofNullable(this.content);
        }

        @Override
        public Optional<String> message() {
            return Optional.empty();
        }

        @Override
        public Optional<Throwable> err() {
            return Optional.empty();
        }

        @Override
        public T orElse(T fallback) {
            return content == null ? fallback : content;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return content == null ? supplier.get() : content;
        }

        @Override
        public <U> FileResult<U> map(Function<? super T, ? extends U> function) {
            return FileResults.ok(function.apply(content));
        }

        @Override
        public <U> FileResult<U> flatMap(Function<? super T, FileResult<U>> function) {
            return Objects.requireNonNull(function.apply(content));
        }
    }

    static final class Err<T> implements FileResult<T> {
        private final String message;
        private final Throwable error;
        Err(String message, Throwable error) { this.message = message; this.error = error; }

        @Override public Optional<T> value() { return Optional.empty(); }
        @Override public Optional<String> message() { return Optional.ofNullable(message); }
        @Override public T orElse(T fb) { return fb; }

        @Override
        public boolean ok() {
            return false;
        }

        @Override
        public Optional<Throwable> err() {
            return Optional.empty();
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public <U> FileResult<U> map(Function<? super T, ? extends U> function) {
            return FileResults.err(message, error);
        }

        @Override
        public <U> FileResult<U> flatMap(Function<? super T, FileResult<U>> function) {
            return FileResults.err(message, error);
        }

        @Override public String toString() { return "Result.err(" + message + ", " + error + ")"; }
    }

}
