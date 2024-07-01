package me.jishuna.jishlib.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class Registry<K, V> implements Iterable<V> {
    private final Map<K, V> registryMap = new ConcurrentHashMap<>();
    private final Map<V, K> inverseMap = new ConcurrentHashMap<>();

    public void register(K key, V value) {
        if (key == null || value == null) {
            return;
        }

        this.registryMap.put(key, value);
        this.inverseMap.put(value, key);
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }

        return this.registryMap.get(key);
    }

    public K getKey(V value) {
        return this.inverseMap.get(value);
    }

    public Optional<V> find(K key) {
        return Optional.ofNullable(get(key));
    }

    public boolean has(K key) {
        if (key == null) {
            return false;
        }

        return this.registryMap.containsKey(key);
    }

    public int size() {
        return this.registryMap.size();
    }

    public boolean isEmpty() {
        return this.registryMap.isEmpty();
    }

    public void forEach(BiConsumer<K, V> consumer) {
        this.registryMap.forEach(consumer);
    }

    @Override
    public Iterator<V> iterator() {
        return this.registryMap.values().iterator();
    }
}
