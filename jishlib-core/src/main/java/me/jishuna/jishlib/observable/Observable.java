package me.jishuna.jishlib.observable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Observable<T> {
    private final List<Consumer<T>> observers = new ArrayList<>();
    private T value;

    public Observable() {
        this(null);
    }

    public Observable(T initialValue) {
        this.value = initialValue;
    }

    public void onChanged(Consumer<T> consumer) {
        this.observers.add(consumer);
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
        this.observers.forEach(consumer -> consumer.accept(value));
    }
}
