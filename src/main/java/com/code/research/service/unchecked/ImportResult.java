package com.code.research.service.unchecked;

import java.nio.file.Path;

/*
public record ImportResult<T>(T value, Path p, Exception e) {
    static <T> ImportResult<T> ok(T value, Path p) {
        return new ImportResult<>(value, p, null);
    }

    static <T> ImportResult<T> err(Path p, Exception err) {
        return new ImportResult<>(null, p, err);
    }

    boolean isOk() {
        return e == null;
    }
}
*/


public record ImportResult<T>(T value, Path file, Exception error) {
    public static <T> ImportResult<T> ok(T v, Path f) {
        return new ImportResult<>(v, f, null);
    }

    public static <T> ImportResult<T> err(Path f, Exception e) {
        return new ImportResult<>(null, f, e);
    }

    public boolean isOk() {
        return error == null;
    }
}