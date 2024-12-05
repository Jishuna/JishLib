package me.jishuna.jishlib.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedRandom<T> implements Iterable<WeightedRandom.WeightedRandomEntry<T>> {
    private final NavigableMap<Double, WeightedRandomEntry<T>> entryMap = new TreeMap<>();
    private double total = 0;

    public WeightedRandom<T> add(double weight, T entry) {
        if (weight <= 0) {
            return this;
        }

        this.total += weight;
        this.entryMap.put(this.total, new WeightedRandomEntry<>(weight, entry));
        return this;
    }

    public T poll() {
        return poll(ThreadLocalRandom.current());
    }

    public T poll(Random random) {
        double value = random.nextDouble() * this.total;
        return this.entryMap.higherEntry(value).getValue().value();
    }

    public boolean isEmpty() {
        return this.entryMap.isEmpty();
    }

    public int size() {
        return this.entryMap.size();
    }

    @Override
    public @NotNull Iterator<WeightedRandomEntry<T>> iterator() {
        return Collections.unmodifiableCollection(entryMap.values()).iterator();
    }

    public record WeightedRandomEntry<T>(double weight, T value) {
    }
}
