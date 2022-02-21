package com.task2.subtask2;


import java.util.*;

public class CombinedList<T> implements List<T> {
    private final List<T> unmodifiableList;
    private final List<T> modifiableList;

    public CombinedList(List<T> unmodifiableList) {
        modifiableList = new com.task1.subtask2.List<>();
        this.unmodifiableList = unmodifiableList;
    }

    public CombinedList() {
        modifiableList = new com.task1.subtask2.List<>();
        this.unmodifiableList = new com.task1.subtask2.List<>();
    }

    @Override
    public int size() {
        return unmodifiableList.size() + modifiableList.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new CombinedIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = Arrays.copyOf(unmodifiableList.toArray(), size());
        System.arraycopy(modifiableList.toArray(), 0, array, unmodifiableList.size(), modifiableList.size());
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        if (array.length < size()) {
            return (T1[]) Arrays.copyOf(toArray(), size());
        }
        System.arraycopy(toArray(), 0, array, 0, size());
        return array;
    }

    @Override
    public boolean add(T element) {
        return modifiableList.add(element);
    }

    @Override
    public void add(int index, T element) {
        checkIndex(index);
        modifiableList.add(index - unmodifiableList.size(), element);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return modifiableList.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        checkIndex(index);
        return modifiableList.addAll(index - unmodifiableList.size(), collection);
    }

    @Override
    public boolean remove(Object object) {
        return modifiableList.remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean check = true;
        Object[] elements = collection.toArray();
        for (Object element : elements) {
            if (indexOf(element) == -1)
                check = false;
            break;
        }
        return check;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        for (Object element : collection) {
            int index = unmodifiableList.indexOf(element);
            if (index != -1) {
                throw new UnsupportedOperationException();
            }
        }
        return modifiableList.removeAll(collection);

    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        for (Object element : collection) {
            int index = unmodifiableList.indexOf(element);
            if (index == -1) {
                throw new UnsupportedOperationException();
            }
        }
        return modifiableList.retainAll(collection);
    }

    @Override
    public void clear() {
        modifiableList.clear();
    }

    @Override
    public T get(int index) {
        if (index < unmodifiableList.size())
            return unmodifiableList.get(index);
        else return modifiableList.get(index - unmodifiableList.size());
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        return modifiableList.set(index - unmodifiableList.size(), element);
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return modifiableList.remove(index - unmodifiableList.size());
    }

    @Override
    public int indexOf(Object object) {

        return unmodifiableList.contains(object) ? unmodifiableList.indexOf(object) : modifiableList.indexOf(object) + unmodifiableList.size();
    }

    @Override
    public int lastIndexOf(Object object) {

        return modifiableList.contains(object) ? modifiableList.lastIndexOf(object) + unmodifiableList.size() : unmodifiableList.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    class CombinedIterator implements Iterator<T> {
        private int cursor = 0;

        CombinedIterator() {
        }

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (cursor < unmodifiableList.size()) {
                return unmodifiableList.get(cursor++);
            }
            return modifiableList.get(cursor++ - unmodifiableList.size());
        }
    }

    private void checkIndex(int index) {
        if (index < unmodifiableList.size()) {
            throw new UnsupportedOperationException();
        }
    }
}
