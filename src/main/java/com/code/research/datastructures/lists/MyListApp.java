package com.code.research.datastructures.lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyListApp {

    public static void main(String[] args) {
        MyList<String> names = new MyList<>(String.class, 5);
        names.add("Alice");
        names.add("Bob");
        names.add(null);
        names.add("Carol");

        log.info("List: {}", names);             // [Alice, Bob, null, Carol]
        log.info("get(1): {} ", names.get(1));    // Bob

        // equals/hashCode test
        MyList<String> other = new MyList<>(String.class, 5);
        other.add("Alice");
        other.add("Bob");
        other.add(null);
        other.add("Carol");

        log.info("equals: {}", names.equals(other));            // true
        log.info("hashCodes: {}", (names.hashCode() == other.hashCode()));       // true
    }

}
