package com.code.research.autoclosable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface FileResult<T>
        permits FileResults.OK, FileResults.Err {
    boolean ok();

    Optional<T> value();

    Optional<String> message();

    Optional<Throwable> err();

    T orElse(T fallback);

    T orElseGet(Supplier<T> supplier);

    <U> FileResult<U> map(Function<? super T, ? extends U> function);

    <U> FileResult<U> flatMap(Function<? super T, FileResult<U>> function);
}
