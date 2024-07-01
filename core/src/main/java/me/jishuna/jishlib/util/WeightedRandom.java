package me.jishuna.jishlib.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedRandom<T> {
    private final NavigableMap<Double, T> map = new TreeMap<>();
    private double total = 0;

    public WeightedRandom<T> add(double weight, T entry) {
        if (weight <= 0) {
            return this;
        }

        this.total += weight;
        this.map.put(this.total, entry);
        return this;
    }

    public T poll() {
        return poll(ThreadLocalRandom.current());
    }

    public T poll(Random random) {
        double value = random.nextDouble() * this.total;
        return this.map.higherEntry(value).getValue();
    }

    public Set<Entry<Double, T>> getEntries() {
        return Collections.unmodifiableSet(this.map.entrySet());
    }

    public Collection<T> getValues() {
        return Collections.unmodifiableCollection(this.map.values());
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public int size() {
        return this.map.size();
    }
}
