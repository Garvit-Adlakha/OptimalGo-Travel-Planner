package com.Minor.OptimalGo.header;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private T[] array;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayList(T... initialElements) {
        array = (T[]) new Object[Math.max(DEFAULT_CAPACITY, initialElements.length)];
        for (T element : initialElements) {
            add(element);
        }
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == array.length) {
            resizeArray();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }
    @Override
    public boolean add(T element) {
        if (size == array.length) {
            resizeArray();
        }
        array[size++] = element;
        return true;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return array[index];
    }
    @Override
    public T set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T oldElement = array[index];
        array[index] = element;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedElement = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null; // Clear the last element
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>() {
            private int cursor = index;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastRet = cursor;
                return array[cursor++];
            }

            @Override
            public boolean hasPrevious() {
                return cursor > 0;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                lastRet = --cursor;
                return array[cursor];
            }

            @Override
            public int nextIndex() {
                return cursor;
            }

            @Override
            public int previousIndex() {
                return cursor - 1;
            }

            @Override
            public void remove() {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            }

            @Override
            public void set(T e) {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                ArrayList.this.set(lastRet, e);
            }

            @Override
            public void add(T e) {
                ArrayList.this.add(cursor++, e);
                lastRet = -1;
            }
        };
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
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[currentIndex++];
            }

            @Override
            public void remove() {
                ArrayList.this.remove(--currentIndex);
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            return (E[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] elements = c.toArray();
        int numNew = elements.length;
        if (numNew == 0) {
            return false;
        }
        if (size + numNew > array.length) {
            resizeArray();
        }
        System.arraycopy(elements, 0, array, size, numNew);
        size += numNew;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Object[] elements = c.toArray();
        int numNew = elements.length;
        if (numNew == 0) {
            return false;
        }
        if (size + numNew > array.length) {
            resizeArray();
        }
        System.arraycopy(array, index, array, index + numNew, size - index);
        System.arraycopy(elements, 0, array, index, numNew);
        size += numNew;
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(array, 0, size, null);
        size = 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            if (remove(it.next())) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("FromIndex: " + fromIndex + ", ToIndex: " + toIndex + ", Size: " + size);
        }
        return new ArrayList<>(Arrays.copyOfRange(array, fromIndex, toIndex));
    }

    @SuppressWarnings("unchecked")
    private void resizeArray() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    // Getter for the internal array (for testing or other purposes)
    public T[] getArray() {
        return Arrays.copyOf(array, size); // Return a copy of the array with the current size
    }

    // Setter for the internal array (for testing or other purposes)
    public void setArray(T[] array) {
        this.array = Arrays.copyOf(array, array.length);
        this.size = array.length;
    }

    // Getter for the size of the array list
    public int getSize() {
        return size;
    }

    // Setter for the size of the array list (for testing or other purposes)
    public void setSize(int size) {
        if (size > array.length) {
            resizeArray();
        }
        this.size = size;
    }
}
