package com.code.research.datastructures.lists;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.Objects;

/**
 * A simple List implementation with a fixed capacity that
 * demonstrates how to implement equals/hashCode (and get/add)
 * without any unchecked‐cast warnings.
 */
public class MyList<E> extends AbstractList<E> {

    /** Runtime type token for E, used for safe casts. */
    private final Class<E> elementType;

    /** Backing storage (cannot create new E[]). */
    private final Object[] elements;

    /** Number of valid elements in the list. */
    private int size;

    /**
     * @param elementType The Class object for E (e.g. String.class).
     * @param capacity    The maximum number of elements.
     */
    public MyList(Class<E> elementType, int capacity) {
        this.elementType = Objects.requireNonNull(elementType, "elementType is null");
        if(capacity < 0){
            throw new IllegalArgumentException("capacity < 0");
        }
        this.elements = new Object[capacity];
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        // Safe cast via Class.cast
        return elementType.cast(elements[index]);
    }

    @Override
    public boolean add(E element) {
        if(size == elements.length){
            throw new IllegalStateException("List is full (capacity: " + elements.length + ")");
        }
        elements[size++] = element;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    // Standard equals/hashCode as per List contract:
    @Override
    public boolean equals(Object o){
        if(o == this) {
            return true;
        }
        if(!(o instanceof java.util.List<?> other)) {
            return false;
        }
        if(other.size() != this.size()) {
            return false;
        }
        Iterator<E> it1 = this.iterator();
        Iterator<?> it2 = other.iterator();
        while(it1.hasNext() && it2.hasNext()) {
            E e1 = it1.next();
            Object e2 = it2.next();
            if(!Objects.equals(e1, e2)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 1;
        for(E e: this){
            h = 31 * h + e.hashCode();
        }
        return h;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < size; i++){
            if(i > 0) {
                sb.append(", ");
            }
            sb.append(elementType.cast(elements[i]));
        }
        return sb.append("]").toString();
    }

}
