package com.Minor.OptimalGo.header;


import com.Minor.OptimalGo.header.LinkedList;

public class Queue<T> {
    private LinkedList<T> queue = new LinkedList<>();

    public Queue() {}

    public Queue(Integer initialElement) {
        enqueue((T) initialElement);
    }

    public Queue(String initialElement) {
        enqueue((T) initialElement);
    }

    public void enqueue(T element) {
        queue.addLast(element);
    }

    public T dequeue() {
        return queue.removeFirst();
    }

    public T peek() {
        return queue.getFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public LinkedList<T> getQueue() {
        return queue;
    }

    public void setQueue(LinkedList<T> queue) {
        this.queue = queue;
    }
}
