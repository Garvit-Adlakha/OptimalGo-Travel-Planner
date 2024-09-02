package com.Minor.OptimalGo.header;

import com.Minor.OptimalGo.header.LinkedList;

public class Deque<T> {
    private LinkedList<T> deque = new LinkedList<>();

    public void addFirst(T element) {
        deque.addFirst(element);
    }

    public void addLast(T element) {
        deque.addLast(element);
    }

    public T removeFirst() {
        return deque.removeFirst();
    }

    public T removeLast() {
        return deque.removeLast();
    }

    public T peekFirst() {
        return deque.getFirst();
    }

    public T peekLast() {
        return deque.getLast();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public int size() {
        return deque.size();
    }

    public LinkedList<T> getDeque() {
        return deque;
    }

    public void setDeque(LinkedList<T> deque) {
        this.deque = deque;
    }
}
