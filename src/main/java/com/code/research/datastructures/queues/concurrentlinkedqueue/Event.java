package com.code.research.datastructures.queues.concurrentlinkedqueue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {

    private final int id;

    private final String description;

    public Event(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{id=" + id + ", description='" + description + "'}";
    }

}
