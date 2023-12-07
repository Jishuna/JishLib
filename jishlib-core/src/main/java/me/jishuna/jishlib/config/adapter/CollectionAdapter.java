package me.jishuna.jishlib.config.adapter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;

public class CollectionAdapter<S, R> implements TypeAdapter<List<S>, Collection<R>> {
    private static Map<Class<?>, Supplier<? extends Collection<?>>> defaults;

    static {
        defaults = new HashMap<>();
        defaults.put(List.class, ArrayList::new);
        defaults.put(Set.class, LinkedHashSet::new);
        defaults.put(Queue.class, ArrayDeque::new);
    }

    private final TypeAdapter<S, R> adapter;
    private final ConfigType<Collection<R>> type;

    @SuppressWarnings("unchecked")
    public CollectionAdapter(ConfigType<?> type) {
        this.adapter = (TypeAdapter<S, R>) ConfigApi.getAdapter(type.getComponentTypes().get(0));
        this.type = (ConfigType<Collection<R>>) type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<List<S>> getSavedType() {
        return (Class<List<S>>) (Object) List.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Collection<R>> getRuntimeType() {
        return (Class<Collection<R>>) (Object) Collection.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<R> read(List<S> value) {
        Collection<R> collection = (Collection<R>) defaults.get(this.type.getType()).get();

        if (this.adapter instanceof TypeAdapterString stringAdapter) {
            value.forEach(o -> collection.add((R) stringAdapter.fromString(o.toString())));
            return collection;
        }

        for (S entry : value) {
            collection.add(this.adapter.read(entry));
        }

        return collection;
    }

    @Override
    public List<S> write(Collection<R> value, List<S> existing, boolean replace) {
        List<S> list = new ArrayList<>();

        for (R entry : value) {
            S saveValue = this.adapter.write(entry, null, replace);
            if (saveValue != null) {
                list.add(saveValue);
            }
        }

        return list;
    }
}
