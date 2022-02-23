package com.task3.subtask1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class UniqueList<T> extends ArrayList<T> {
    private UniqueList(Collection<? extends T> collection) {
    }

    public UniqueList(int initialCapacity) {
        super(initialCapacity);
    }

    public UniqueList() {
        super();
    }

    private void checkExisting(T element) {
        if (indexOf(element) > -1)
            throw new UnsupportedOperationException("Object '" + element + "' already exists");
    }

    private void checkExisting(Collection<? extends T> collection) {
        for (T element : collection) {
            if (indexOf(element) > -1 || Collections.frequency(collection,element)>1)
                throw new UnsupportedOperationException("Object '" + element + "' already exists");

        }
    }

    @Override
    public T set(int index, T element) {
        if (get(index) == null && element == null || get(index).equals(element)) return element;
        checkExisting(element);
        return super.set(index, element);
    }

    @Override
    public boolean add(T element) {
        checkExisting(element);
        return super.add(element);
    }

    @Override
    public void add(int index, T element) {
        checkExisting(element);
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        checkExisting(collection);
        return super.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        checkExisting(collection);
        return super.addAll(index, collection);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = size();
        for (int i = 0; modCount == expectedModCount && i < size; i++) {
            set(i, operator.apply((T) get(i)));
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }
}
