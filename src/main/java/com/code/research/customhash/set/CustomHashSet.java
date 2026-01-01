package com.code.research.customhash.set;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * CustomHashSet<E> — a HashSet-like implementation backed by CustomHashTable.
 *
 * Properties:
 * - No duplicates
 * - Allows one null element
 * - Average O(1) add/contains/remove (with decent hashing)
 * - Resizes at (capacity * loadFactor)
 * - Fail-fast iterator (ConcurrentModificationException)
 *
 * Notes:
 * - Uses separate chaining (linked lists per bucket).
 * - Does not implement JDK's tree-bins optimizations (educational + clean).
 */
public final class CustomHashSet<E> implements Set<E>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private final CustomHashTable<E> table;

    public CustomHashSet() {
        this.table = new CustomHashTable<>();
    }

    public CustomHashSet(int initialCapacity) {
        this.table = new CustomHashTable<>(initialCapacity);
    }

    public CustomHashSet(int initialCapacity, float loadFactor) {
        this.table = new CustomHashTable<>(initialCapacity, loadFactor);
    }

    public CustomHashSet(Collection<? extends E> c) {
        this.table = new CustomHashTable<>(Math.max(16, (int) (c.size() / 0.75f) + 1), 0.75f);
        addAll(c);
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public boolean isEmpty() {
        return table.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return table.contains(o);
    }

    @Override
    public boolean add(E e) {
        return table.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return table.remove(o);
    }

    @Override
    public void clear() {
        table.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return table.iterator();
    }

    @Override
    public Spliterator<E> spliterator() {
        // Good enough: DISTINCT flag. (No ordering guaranteed.)
        return Spliterators.spliterator(this, Spliterator.DISTINCT);
    }

    // ---------- Bulk ops ----------

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c, "collection");
        if (c.isEmpty()) return false;

        table.ensureCapacityFor(size() + c.size());

        boolean modified = false;
        for (E e : c) modified |= add(e);
        return modified;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection");
        for (Object o : c) if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection");
        boolean modified = false;
        for (Object o : c) modified |= remove(o);
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection");
        boolean modified = false;

        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            if (!c.contains(e)) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    // ---------- Array conversions ----------

    @Override
    public Object[] toArray() {
        Object[] a = new Object[size()];
        int i = 0;
        for (E e : this) a[i++] = e;
        return a;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        Objects.requireNonNull(a, "array");

        int sz = size();
        T[] r = (a.length >= sz)
                ? a
                : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), sz);

        int i = 0;
        for (E e : this) r[i++] = (T) e;

        if (r.length > sz) r[sz] = null;
        return r;
    }

    // ---------- Set contract ----------

    @Override
    public int hashCode() {
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
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action, "action");
        for (E e : this) action.accept(e);
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext()) return "[]";

        StringBuilder sb = new StringBuilder().append('[');
        while (true) {
            E e = it.next();
            sb.append(e == this ? "(this Set)" : e);
            if (!it.hasNext()) return sb.append(']').toString();
            sb.append(", ");
        }
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public CustomHashSet<E> clone() {
        CustomHashSet<E> copy = new CustomHashSet<>(table.capacity(), table.loadFactor());
        copy.addAll(this);
        return copy;
    }
}
