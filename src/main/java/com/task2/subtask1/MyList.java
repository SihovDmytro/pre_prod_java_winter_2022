package com.task2.subtask1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


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
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyCopyOnWriteIterator(array);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        if (array.length < size) {
            return Arrays.copyOf(array, size);
        }
        System.arraycopy(this.array, 0, array, 0, size);
        return array;
    }

    @Override
    public boolean add(T element) {
        checkCapacity(1);
        array[size++] = element;
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
    public boolean addAll(Collection<? extends T> collection) {
        Object[] newElements = collection.toArray();
        for (Object element : newElements) {
            add((T) element);
        }
        return newElements.length != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        checkIndex(index);
        Object[] newElements = collection.toArray();
        checkCapacity(newElements.length);
        System.arraycopy(array, index, array, index + newElements.length, size - index);
        System.arraycopy(newElements, 0, array, index, newElements.length);
        size += newElements.length;
        return newElements.length != 0;
    }

    @Override
    public boolean remove(Object object) {
        if (object == null) {
            for (int i = 0; i < size; i++)
                if (array[i] == null) {
                    remove(i);
                    return true;
                }
        } else {
            for (int i = 0; i < size; i++)
                if (object.equals(array[i])) {
                    remove(i);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean check = true;
        Object[] elements = collection.toArray();
        for (Object element : elements) {
            if (indexOf(element) == -1)
                check = false;
        }
        return check;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Object[] elements = collection.toArray();
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
    public boolean retainAll(Collection<?> collection) {
        boolean modifyed = false;
        int countOfRetained = 0;
        int i = 0;
        Object[] array2 = new Object[array.length];
        for (; i < size; i++) {
            if (collection.contains(array[i])) {
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
    public int indexOf(Object object) {
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        if (object == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i].equals(object)) {
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

    class MyCopyOnWriteIterator implements Iterator<T> {
        private int cursor = 0;
        private Object[] arr;


        public MyCopyOnWriteIterator(Object[] elements) {
            arr = elements;
        }

        @Override
        public boolean hasNext() {
            return cursor < arr.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) arr[cursor++];
        }


    }

}
