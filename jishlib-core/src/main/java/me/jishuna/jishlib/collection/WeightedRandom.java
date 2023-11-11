package me.jishuna.jishlib.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple weighted random. <br>
 * Can contain and poll a collection of objects with different weights.
 *
 * @param <T> the type of object
 */
public class WeightedRandom<T> {
    private final NavigableMap<Double, T> map = new TreeMap<>();
    private double total = 0;

    /**
     * Adds an entry to this WeightedRandom with the given weight
     *
     * @param weight the weight for this entry, higher is more common
     * @param entry  the entry to add
     * @return this WeightedRandom, for chaining
     */
    public WeightedRandom<T> add(double weight, T entry) {
        if (weight <= 0) {
            return this;
        }

        this.total += weight;
        this.map.put(this.total, entry);
        return this;
    }

    /**
     * Polls a single entry from this WeightedRandom using the ThreadLocalRandom of
     * the current thread.
     *
     * @return a random entry
     */
    public T poll() {
        return poll(ThreadLocalRandom.current());
    }

    /**
     * Polls a single entry from this WeightedRandom using the provided random
     * instance.
     *
     * @return a random entry
     */
    public T poll(Random random) {
        double value = random.nextDouble() * this.total;
        return this.map.higherEntry(value).getValue();
    }

    /**
     * Gets an immutable set of the entries in this WeightedRandom.
     *
     * @return a set of entries
     */
    public Set<Entry<Double, T>> getEntries() {
        return Collections.unmodifiableSet(this.map.entrySet());
    }

    /**
     * Gets an immutable collection of the values in this WeightedRandom.
     *
     * @return a collection of values
     */
    public Collection<T> getValues() {
        return Collections.unmodifiableCollection(this.map.values());
    }

    /**
     * Checks if this WeightedRandom is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /**
     * Gets the number of entries in this WeightedRandom.
     *
     * @return the number of entries
     */
    public int size() {
        return this.map.size();
    }
}
