package model.adt;

import exception.MyException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> implements MyIStack<T> {
    private Deque<T> stack;

    public MyStack() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public synchronized T pop() throws MyException { // Adaugat synchronized
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty. Cannot pop.");
        }
        return stack.pop();
    }

    @Override
    public synchronized void push(T v) { // Adaugat synchronized
        stack.push(v);
    }

    @Override
    public synchronized boolean isEmpty() { // Adaugat synchronized
        return stack.isEmpty();
    }

    @Override
    public synchronized T peek() throws MyException { // Adaugat synchronized
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty. Cannot peek.");
        }
        return stack.peek();
    }

    @Override
    public String toString() {
        // Sincronizam explicit pe obiectul stack in timpul iterarii
        // pentru a preveni ConcurrentModificationException cand GUI-ul face refresh
        synchronized (this) {
            StringBuilder sb = new StringBuilder();
            for (T elem : stack) {
                sb.append(elem.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    // Dacă ai nevoie de o metodă care returnează lista pentru GUI (reverse order),
    // asigură-te că și ea este sincronizată!
    public synchronized java.util.List<T> getValues() {
        return new java.util.ArrayList<>(stack);
    }
}