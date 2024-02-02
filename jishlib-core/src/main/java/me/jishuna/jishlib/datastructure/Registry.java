package me.jishuna.jishlib.datastructure;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Registry<K, V> implements Iterable<Entry<K, V>> {
    private final Map<K, V> registryMap;

    public Registry() {
        this(HashMap::new);
    }

    public Registry(Supplier<Map<K, V>> supplier) {
        this.registryMap = supplier.get();
    }

    public void register(K key, V value) {
        register(key, value, false);
    }

    public void register(K key, V value, boolean replace) {
        if (key == null) {
            return;
        }

        if (replace) {
            this.registryMap.put(key, value);
        } else {
            this.registryMap.putIfAbsent(key, value);
        }
    }

    public void register(Supplier<K> keySupplier, V value) {
        register(keySupplier, value, false);
    }

    public void register(Supplier<K> keySupplier, V value, boolean replace) {
        register(keySupplier.get(), value, replace);
    }

    public void register(Function<V, K> keyFunction, V value) {
        register(keyFunction, value, false);
    }

    public void register(Function<V, K> keyFunction, V value, boolean replace) {
        register(keyFunction.apply(value), value, replace);
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }

        return this.registryMap.get(key);
    }

    public V get(K key, V def) {
        if (key == null) {
            return null;
        }

        V value = this.registryMap.get(key);
        return value != null ? value : def;
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

    public V remove(K key) {
        if (key == null) {
            return null;
        }

        return this.registryMap.remove(key);
    }

    public void clear() {
        this.registryMap.clear();
    }

    public Collection<K> getKeys() {
        return Collections.unmodifiableSet(this.registryMap.keySet());
    }

    public Collection<V> getValues() {
        return Collections.unmodifiableCollection(this.registryMap.values());
    }

    public Collection<Entry<K, V>> getEntries() {
        return Collections.unmodifiableCollection(this.registryMap.entrySet());
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return getEntries().iterator();
    }

    public int size() {
        return this.registryMap.size();
    }

    public boolean isEmpty() {
        return this.registryMap.isEmpty();
    }
}
