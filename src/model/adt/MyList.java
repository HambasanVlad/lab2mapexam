package model.adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private List<T> list;

    public MyList() {
        // CHANGE 1: Use a synchronized wrapper to make the list thread-safe.
        this.list = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public String toString() {
        // CHANGE 2: When iterating over a synchronized list, you MUST use a synchronized block.
        // This prevents other threads from modifying the list while you are reading it (avoiding ConcurrentModificationException).
        synchronized (list) {
            // Keep your original formatting logic
            StringBuilder sb = new StringBuilder();
            for (T elem : list) {
                sb.append(elem.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    // Optional: If you need access to the raw list in other parts of the app
    public List<T> getList() {
        return list;
    }
}