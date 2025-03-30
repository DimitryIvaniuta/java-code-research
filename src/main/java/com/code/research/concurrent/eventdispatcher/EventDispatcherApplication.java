package com.code.research.concurrent.eventdispatcher;

import lombok.extern.slf4j.Slf4j;

/**
 * MainDemo demonstrates the usage of both EventDispatcher and UniqueSubscriberManager.
 */
@Slf4j
public class EventDispatcherApplication {

    public static void main(String[] args) {
        // Demonstrate EventDispatcher with CopyOnWriteArrayList
        EventDispatcher dispatcher = new EventDispatcher();

        // Register some listeners using lambda expressions
        dispatcher.addListener(event -> log.info("Listener 1 received: {}", event));
        dispatcher.addListener(event -> log.info("Listener 2 received: {}", event));
        dispatcher.addListener(event -> log.info("Listener 3 received: {}", event));

        log.info("Dispatching event 'Event-A' to listeners:");
        dispatcher.dispatchEvent("Event-A");

        // Demonstrate UniqueSubscriberManager with CopyOnWriteArraySet
        UniqueSubscriberManager subscriberManager = new UniqueSubscriberManager();
        subscriberManager.addSubscriber("user1");
        subscriberManager.addSubscriber("user2");
        subscriberManager.addSubscriber("user3");
        // Attempt to add a duplicate
        boolean added = subscriberManager.addSubscriber("user2");
        log.info("Attempt to add duplicate subscriber 'user2': {}", added);

        log.info("Current subscribers: {}", subscriberManager.getSubscribers());
    }
    
}
