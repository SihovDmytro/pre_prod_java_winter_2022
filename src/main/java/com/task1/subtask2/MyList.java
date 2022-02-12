package com.task1.subtask2;


import java.util.*;
import java.util.function.Predicate;

public class MyList<T> implements List<T> {
    private Object[] array;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 5;
    public MyList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    public MyList(int capacity) {
        if (capacity > 0)
            array = new Object[capacity];
        else {
            throw new IllegalArgumentException("Invalid capacity: " + capacity);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    public Iterator<T> iterator(Predicate<T> predicate) {
        return new MyIteratorWithPredicate<>(predicate);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return Arrays.copyOf(a, size);
        }
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T t) {
        checkCapacity(1);
        array[size++] = t;
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkIndex(index);
        checkCapacity(1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] newElements = c.toArray();
        checkCapacity(newElements.length);
        System.arraycopy(newElements, 0, array, size, newElements.length);
        size += newElements.length;
        return newElements.length != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        Object[] newElements = c.toArray();
        checkCapacity(newElements.length);
        System.arraycopy(array, index, array, index + newElements.length, size - index);
        System.arraycopy(newElements, 0, array, index, newElements.length);
        size += newElements.length;
        return newElements.length != 0;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (array[i] == null) {
                    remove(i);
                    return true;
                }
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(array[i])) {
                    remove(i);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean check = true;
        Object[] elements = c.toArray();
        for (Object element : elements) {
            if (indexOf(element) == -1)
                check = false;
        }
        return check;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] elements = c.toArray();
        boolean modified = false;
        for (Object element : elements) {
            int index = indexOf(element);
            if (index != -1) {
                remove(index);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modifyed = false;
        int countOfRetained = 0;
        int i = 0;
        Object[] array2 = new Object[array.length];
        for (; i < size; i++) {
            if (c.contains(array[i])) {
                array2[countOfRetained++] = array[i];
            }
        }
        if (countOfRetained > 0) {
            System.arraycopy(array2, 0, array, 0, array.length);
            size = countOfRetained;
            modifyed = true;
        }
        return modifyed;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        T oldElement = (T) array[index];
        array[index] = element;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[size--] = null;
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (array[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    private void checkCapacity(int count) {
        if (array.length < size + count) {
            array = Arrays.copyOf(array, (array.length + count) * 2);
        }

    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Size = " + size);
    }

    class MyIterator<T1> implements Iterator<T1> {
        private int index = 0;

        public MyIterator() {
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T1 next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T1) array[index++];
        }

        public boolean hasNext(Predicate<T> predicate) {
            return index < size && predicate.test((T) array[index]);
        }

        public T next(Predicate<T> predicate) {
            if (!hasNext(predicate)) {
                throw new NoSuchElementException();
            }
            return (T) array[index++];
        }
    }

    class MyIteratorWithPredicate<T1> implements Iterator<T1> {
        private int index = 0;
        private Predicate<T1> predicate;

        public MyIteratorWithPredicate(Predicate<T1> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            if (index >= size) return false;
            int i = index;
            for (; i < size; i++) {
                if (predicate.test((T1) array[i]))
                    return true;
            }
            return false;
        }

        @Override
        public T1 next() {
            int i = index;
            for (; i < size; i++) {
                if (predicate.test((T1) array[i])) {
                    index = i + 1;
                    return (T1) array[index - 1];
                }
            }
            throw new NoSuchElementException();
        }
    }
}
