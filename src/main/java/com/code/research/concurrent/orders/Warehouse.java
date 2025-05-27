package com.code.research.concurrent.orders;

import com.code.research.concurrent.orders.domain.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Warehouse {
    private final Deque<Order> orders;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    private final int capacity;

    public Warehouse(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
        this.orders = new ArrayDeque<>(capacity);
        this.lock = new ReentrantLock(true);
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * Blocks until space is available, then adds the given order at the tail.
     *
     * @param order the order to store
     * @throws InterruptedException if the thread is interrupted while waiting
     * @throws NullPointerException if order is null
     */
    public void submitOrder(Order order) throws InterruptedException {
        Objects.requireNonNull(order, "order");
        lock.lockInterruptibly();
        try {
            while (orders.size() == capacity) {
                log.info("Warehouse full—waiting to submit order {}", order.getId());
                notFull.await();
            }
            orders.addLast(order);
            log.info("Order {} submitted", order.getId());
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Blocks until an order is available, then removes and returns
     * the head of the deque.
     *
     * @return the next order to process
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public Order retrieveOrder() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (orders.isEmpty()) {
                log.info("Warehouse empty—waiting for orders");
                notEmpty.await();
            }
            Order order = orders.removeFirst();
            log.info("Order {} retrieved", order.getId());
            notFull.signal();
            return order;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Tries to submit an order within the given timeout.
     *
     * @param order   the order to add
     * @param timeout max time to wait
     * @param unit    time unit of the timeout
     * @return true if the order was accepted, false if timed out
     * @throws InterruptedException if interrupted while waiting
     * @throws NullPointerException if order or unit is null
     */
    public boolean trySubmitOrder(Order order, long timeout, TimeUnit unit)
            throws InterruptedException {
        Objects.requireNonNull(order, "order");
        Objects.requireNonNull(unit, "unit");
        long nanos = unit.toNanos(timeout);
        lock.lockInterruptibly();
        try {
            while (orders.size() == capacity) {
                if (nanos <= 0L) {
                    return false;
                }
                nanos = notFull.awaitNanos(nanos);
            }
            orders.addLast(order);
            notEmpty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Tries to retrieve an order within the given timeout.
     *
     * @param timeout max time to wait
     * @param unit    time unit of the timeout
     * @return the order, or null if timed out
     * @throws InterruptedException if interrupted while waiting
     * @throws NullPointerException if unit is null
     */
    public Order tryRetrieveOrder(long timeout, TimeUnit unit)
            throws InterruptedException {

        Objects.requireNonNull(unit, "unit");
        long nanos = unit.toNanos(timeout);
        lock.lockInterruptibly();
        try {
            while (orders.isEmpty()) {
                if (nanos <= 0L) {
                    return null;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }
            Order order = orders.removeFirst();
            notFull.signal();
            return order;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns an unmodifiable snapshot of pending orders.
     *
     * @return list of orders in queue order
     */
    public List<Order> snapshotPendingOrders() {
        lock.lock();
        try {
            return Collections.unmodifiableList(List.copyOf(orders));
        } finally {
            lock.unlock();
        }
    }

    public int getPendingOrderCount() {
        lock.lock();
        try{
            return orders.size();
        } finally {
            lock.unlock();
        }
    }
}
