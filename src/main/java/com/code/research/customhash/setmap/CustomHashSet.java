package com.code.research.customhash.setmap;

import java.io.Serializable;
import java.util.*;

/**
 * JDK-like HashSet implementation backed by a map:
 *   element is stored as key in CustomHashMap<E, Object>
 *   value is a single shared dummy marker (PRESENT)
 *
 * This mirrors java.util.HashSet design.
 */
public final class CustomHashSet<E> implements Set<E>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Object PRESENT = new Object();

    private final CustomHashMap<E, Object> map;

    public CustomHashSet() {
        this.map = new CustomHashMap<>();
    }

    public CustomHashSet(int initialCapacity) {
        this.map = new CustomHashMap<>(initialCapacity);
    }

    public CustomHashSet(int initialCapacity, float loadFactor) {
        this.map = new CustomHashMap<>(initialCapacity, loadFactor);
    }

    public CustomHashSet(Collection<? extends E> c) {
        this.map = new CustomHashMap<>(Math.max(16, (int) (c.size() / 0.75f) + 1), 0.75f);
        addAll(c);
    }

    @Override public int size() { return map.size(); }
    @Override public boolean isEmpty() { return map.isEmpty(); }
    @Override public boolean contains(Object o) { return map.containsKey(o); }
    @Override public Iterator<E> iterator() { return map.keySet().iterator(); }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override public void clear() { map.clear(); }

    @Override
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return map.keySet().toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection");
        for (Object o : c) if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c, "collection");
        boolean modified = false;
        for (E e : c) modified |= add(e);
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection");
        return map.keySet().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection");
        return map.keySet().removeAll(c);
    }

    @Override
    public int hashCode() {
        // Set contract: sum of element hash codes
        int h = 0;
        for (E e : this) h += Objects.hashCode(e);
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Set<?> other)) return false;
        if (other.size() != size()) return false;
        return containsAll(other);
    }

    @Override
    public String toString() {
        return map.keySet().toString();
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public CustomHashSet<E> clone() {
        CustomHashSet<E> copy = new CustomHashSet<>(map.capacity(), map.loadFactor());
        copy.addAll(this);
        return copy;
    }
}
