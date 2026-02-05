package model.adt;

import exception.MyException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ArrayList;

public class MyStack<T> implements MyIStack<T> {
    private Deque<T> stack;

    public MyStack() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public synchronized T pop() throws MyException {
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty. Cannot pop.");
        }
        return stack.pop();
    }

    @Override
    public synchronized void push(T v) {
        stack.push(v);
    }

    @Override
    public synchronized boolean isEmpty() {
        return stack.isEmpty();
    }

    // Am scos @Override temporar. Dacă primești eroare că metoda lipsește din MyIStack,
    // adaug-o în interfața MyIStack.java mai întâi!
    public synchronized T peek() throws MyException {
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty. Cannot peek.");
        }
        return stack.peek();
    }

    @Override
    public String toString() {
        // Sincronizare esențială pentru afișare fără erori
        synchronized (this) {
            StringBuilder sb = new StringBuilder();
            for (T elem : stack) {
                sb.append(elem.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    // Metodă sincronizată pentru GUI
    public synchronized List<T> getValues() {
        return new ArrayList<>(stack);
    }
}