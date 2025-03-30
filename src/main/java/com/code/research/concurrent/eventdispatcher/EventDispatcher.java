package com.code.research.concurrent.eventdispatcher;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * EventDispatcher demonstrates the use of CopyOnWriteArrayList to maintain a thread-safe list
 * of event listeners, allowing concurrent reads while minimizing locking overhead.
 */
public class EventDispatcher {

    /**
     * A thread-safe list of event listeners.
     */
    private final CopyOnWriteArrayList<EventListener> listeners;

    /**
     * Constructs an EventDispatcher.
     */
    public EventDispatcher() {
        this.listeners = new CopyOnWriteArrayList<>();
    }

    /**
     * Registers a new event listener.
     *
     * @param listener the listener to register
     */
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters an existing event listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Dispatches an event to all registered listeners.
     *
     * @param event the event to dispatch
     */
    public void dispatchEvent(String event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    /**
     * EventListener defines a simple interface for handling events.
     */
    public interface EventListener {
        /**
         * Called when an event is dispatched.
         *
         * @param event the event message
         */
        void onEvent(String event);
    }

}
