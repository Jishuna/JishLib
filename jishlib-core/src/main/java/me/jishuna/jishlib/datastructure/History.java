package me.jishuna.jishlib.datastructure;

import java.util.ArrayDeque;
import java.util.Deque;

public class History<T> {
    private final Deque<T> history = new ArrayDeque<>();
    private T active;

    public History() {
        this(null);
    }

    public History(T initial) {
        this.active = initial;
    }

    public T getActive() {
        return this.active;
    }

    public void setActive(T value, boolean recordHistory) {
        if (recordHistory && this.active != null) {
            this.history.addFirst(this.active);
        }
        this.active = value;
    }

    public void addHistory(T value) {
        this.history.addFirst(value);
    }

    public boolean hasPrevious() {
        return !this.history.isEmpty();
    }

    public boolean restorePrevious() {
        if (!hasPrevious()) {
            return false;
        }

        this.active = this.history.poll();
        return true;
    }

    public int getHistorySize() {
        return this.history.size();
    }

    public T getPrevious() {
        return this.history.peek();
    }

    public T pollPrevious() {
        return this.history.poll();
    }
}
