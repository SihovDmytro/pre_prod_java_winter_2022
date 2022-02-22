package com.task2.subtask1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CopyOnWriteList<T> implements List<T> {

    private volatile Object[] array;

    public CopyOnWriteList() {
        array = new Object[0];
    }

    final Object[] getArray() {
        return array;
    }

    final void setArray(Object[] newArray) {
        array = newArray;
    }

    @Override
    public int size() {
        return getArray().length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object, getArray()) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new CopyOnWriteIterator(getArray());
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(getArray(), size());
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        int sizeOfList = size();
        Object[] objects = getArray();
        if (array.length < sizeOfList) {
            return (T1[]) Arrays.copyOf(objects, sizeOfList);
        }
        System.arraycopy(objects, 0, array, 0, sizeOfList);
        return array;
    }

    @Override
    public boolean add(T element) {
            Object[] newArray = copyAndIncrementLength(getArray(),1);
            newArray[newArray.length-1] = element;
            setArray(newArray);
            return true;
    }

    @Override
    public void add(int index, T element) {
        Object[] oldArray = getArray();
        checkIndexForAddMethods(index,oldArray);
        Object[] newArray = new Object[oldArray.length + 1];
        int shift = oldArray.length - index;
        System.arraycopy(oldArray, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(oldArray, index, newArray, index + 1, shift );
        setArray(newArray);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if (collection.size() == 0) return false;
        Object[] oldArray = getArray();
        Object[] elementsToAdd = collection.toArray();
        Object[] newArray = copyAndIncrementLength(oldArray, collection.size());
        System.arraycopy(elementsToAdd, 0, newArray, oldArray.length, elementsToAdd.length);
        setArray(newArray);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        if (collection.size() == 0) return false;
        Object[] oldArray = getArray();
        Object[] elementsToAdd = collection.toArray();
        checkIndexForAddMethods(index,oldArray);
        Object[] newArray = copyAndIncrementLength(oldArray,collection.size());
        System.arraycopy(newArray, index, newArray, index + elementsToAdd.length, oldArray.length - index - 1);
        System.arraycopy(elementsToAdd, 0, newArray, index, elementsToAdd.length);
        setArray(newArray);
        return true;
    }

    @Override
    public boolean remove(Object object) {
        Object[] objects = getArray();
        int indexOfObject = indexOf(object, objects);
        if (indexOfObject == -1) return false;
        remove(indexOfObject);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean check = true;
        Object[] objects = getArray();
        for (Object element : collection) {
            if (indexOf(element, objects) == -1) {
                check = false;
                break;
            }
        }
        return check;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection.size() == 0) return false;
        Object[] oldArray = getArray();
        Object[] newArray = new Object[oldArray.length];
        int sizeOfNewArray = 0;
        boolean modified = false;
        for (int i = 0; i < oldArray.length; i++) {
            if (!collection.contains(oldArray[i])) {
                newArray[sizeOfNewArray++] = oldArray[i];
            } else modified = true;
        }

        setArray(Arrays.copyOf(newArray, sizeOfNewArray));
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection.size() == 0) return false;
        Object[] oldArray = getArray();
        Object[] newArray = new Object[oldArray.length];
        int sizeOfNewArray = 0;
        boolean modified = false;
        for (int i = 0; i < oldArray.length; i++) {
            if (collection.contains(oldArray[i])) {
                newArray[sizeOfNewArray++] = oldArray[i];
            } else modified = true;
        }

        setArray(Arrays.copyOf(newArray, sizeOfNewArray));
        return modified;
    }

    @Override
    public void clear() {
        setArray(new Object[0]);
    }

    @Override
    public T get(int index) {
        Object[] objects = getArray();
        checkIndex(index, objects);
        return (T) objects[index];
    }

    @Override
    public T set(int index, T newElement) {
        Object[] objects = getArray();
        checkIndex(index, objects);
        Object oldElement = objects[index];
        objects[index] = newElement;
        setArray(objects);
        return (T) oldElement;
    }

    @Override
    public T remove(int index) {
        Object[] objects = getArray();
        checkIndex(index, objects);
        Object removedElement = objects[index];
        Object[] newArray = new Object[objects.length - 1];
        System.arraycopy(objects, 0, newArray, 0, index);
        System.arraycopy(objects, index + 1, newArray, index, objects.length - index - 1);
        setArray(newArray);
        return (T) removedElement;
    }

    @Override
    public int indexOf(Object object) {
        return indexOf(object, getArray());
    }

    private int indexOf(Object object, Object[] elements) {
        if (object == null) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < elements.length; i++) {
                if (object.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        Object[] elements = getArray();
        if (object == null) {
            for (int i = elements.length - 1; i >= 0; i--) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = elements.length - 1; i >= 0; i--) {
                if (elements[i].equals(object)) {
                    return i;
                }
            }
        }
        return -1;
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

    class CopyOnWriteIterator implements Iterator<T> {
        private int cursor = 0;
        private Object[] arr;

        public CopyOnWriteIterator(Object[] elements) {
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

    private static void checkIndex(int index, Object[] objects) {
        if (index < 0 || index >= objects.length) throw new IndexOutOfBoundsException();
    }

    private static void checkIndexForAddMethods(int index, Object[] objects) {
        if (index < 0 || index > objects.length) throw new IndexOutOfBoundsException();
    }

    private Object[] copyAndIncrementLength( Object[] oldArray, int count)
    {
        return Arrays.copyOf(oldArray, oldArray.length + count);
    }

}
