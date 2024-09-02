package com.Minor.OptimalGo.header;


public class ArrayList<T> {
    private T[] array;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
    }


    public void add(T element)
    {
        if (size == array.length)
        {
            resizeArray();
        }
        array[size++] = element;
    }


    public T get(int index)
    {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return array[index];
    }


    public int size()
    {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedElement = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null; // Clear the last element
        size--;
        return removedElement;
    }


    @SuppressWarnings("unchecked")
    private void resizeArray() {
        T[] newArray = (T[]) new Object[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    // Getter for the internal array (for testing or other purposes)
    public T[] getArray() {
        return array;
    }

    // Setter for the internal array (for testing or other purposes)
    public void setArray(T[] array) {
        this.array = array;
        this.size = array.length;
    }

    // Getter for the size of the array list
    public int getSize() {
        return size;
    }

    // Setter for the size of the array list (for testing or other purposes)
    public void setSize(int size) {
        this.size = size;
    }
}
