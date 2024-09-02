

package com.Minor.OptimalGo.header;

import com.Minor.OptimalGo.header.LinkedList;

public class Stack<T> {
    private LinkedList<T> stack = new LinkedList<>();

    public void push(T element) {
        stack.addFirst(element);
    }

    public T pop() {
        return stack.removeFirst();
    }

    public T peek() {
        return stack.getFirst();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public LinkedList<T> getStack() {
        return stack;
    }

    public void setStack(LinkedList<T> stack) {
        this.stack = stack;
    }
}
