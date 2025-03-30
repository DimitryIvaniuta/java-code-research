package com.code.research.concurrent.eventdispatcher;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * UniqueSubscriberManager demonstrates the use of CopyOnWriteArraySet to maintain a unique
 * set of subscribers. This collection is ideal when duplicate entries are not allowed,
 * and read operations greatly outnumber writes
 */
public class UniqueSubscriberManager {

    /**
     * A thread-safe set of unique subscriber IDs.
     */
    private final CopyOnWriteArraySet<String> subscribers;

    /**
     * Constructs a UniqueSubscriberManager.
     */
    public UniqueSubscriberManager() {
        this.subscribers = new CopyOnWriteArraySet<>();
    }

    /**
     * Adds a subscriber.
     *
     * @param subscriberId the subscriber's unique identifier
     * @return true if the subscriber was added, false if already present
     */
    public boolean addSubscriber(String subscriberId) {
        return subscribers.add(subscriberId);
    }

    /**
     * Removes a subscriber.
     *
     * @param subscriberId the subscriber's unique identifier
     * @return true if the subscriber was removed, false otherwise
     */
    public boolean removeSubscriber(String subscriberId) {
        return subscribers.remove(subscriberId);
    }

    /**
     * Returns the current set of subscribers.
     *
     * @return a set of subscriber IDs
     */
    public Set<String> getSubscribers() {
        return subscribers;
    }
}
