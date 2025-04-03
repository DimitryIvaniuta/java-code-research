package com.code.research.datastructures.queues.linkedblockingqueue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class LogEntry {

    private final int id;

    private final String message;

}
