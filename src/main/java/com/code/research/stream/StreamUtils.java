package com.code.research.stream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {


    /**
     * Lazily partitions the source stream into lists of up to `batchSize`.
     */
    public static <T> Stream<List<T>> partition(Stream<T> source, int batchSize) {
        Spliterator<T> spl = source.spliterator();

        return StreamSupport.stream(new Spliterator<>() {
            @Override
            public boolean tryAdvance(Consumer<? super List<T>> action) {
                Iterator<T> it = Spliterators.iterator(spl);
                if (!it.hasNext()) return false;

                List<T> batch = new ArrayList<>(batchSize);
                for (int i = 0; i < batchSize && it.hasNext(); i++) {
                    batch.add(it.next());
                }

                action.accept(batch);
                return true;
            }

            @Override public Spliterator<List<T>> trySplit() { return null; }
            @Override public long estimateSize() { return Long.MAX_VALUE; }
            @Override public int characteristics() {
                return spl.characteristics() & ~Spliterator.SIZED;
            }
        }, false);
    }

}

