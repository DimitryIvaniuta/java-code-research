package com.code.research.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public class StreamUtilsApp {

    public static void main(String[] args) {

        Stream<MyRecord> bigStream = Stream.of(
                new MyRecord(1, "name1"),
                new MyRecord(2, "name2"),
                new MyRecord(3, "name3")
        );
        StreamUtils.partition(bigStream, 100)
                .forEach(batch -> {
                    log.info("Process record: {}", batch.toString());
                });
    }


    private record MyRecord(int id, String name) {
    }
}
