package me.jishuna.jishlib.data.adapter;

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
import me.jishuna.jishlib.data.DataType;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;

public class CollectionAdapter<T> implements TypeAdapter<ListDataHolder, Collection<T>> {
    private static Map<Class<?>, Supplier<? extends Collection<?>>> defaults;

    static {
        defaults = new HashMap<>();
        defaults.put(List.class, ArrayList::new);
        defaults.put(Set.class, LinkedHashSet::new);
        defaults.put(Queue.class, ArrayDeque::new);
    }

    private final TypeAdapter<DataHolder<?>, T> adapter;
    private final DataType<Collection<T>> type;

    @SuppressWarnings("unchecked")
    public CollectionAdapter(DataType<?> type) {
        this.adapter = (TypeAdapter<DataHolder<?>, T>) TypeAdapterRegistry.getAdapter(type.getComponentTypes().get(0));
        this.type = (DataType<Collection<T>>) type;
    }

    @Override
    public Class<ListDataHolder> getObjectType() {
        return ListDataHolder.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<T> deserialize(ListDataHolder data) {
        Collection<T> collection = (Collection<T>) defaults.get(this.type.getType()).get();

        data.forEach(o -> collection.add(this.adapter.deserialize(o)));
        return collection;
    }

    @Override
    public ListDataHolder serialize(Collection<T> value) {
        List<DataHolder<?>> dataList = new ArrayList<>();

        value.forEach(v -> dataList.add(this.adapter.serialize(v)));
        return ListDataHolder.of(dataList);
    }

    @Override
    public Collection<T> fromString(String value) {
        return null;
    }

    @Override
    public String toString(Collection<T> value) {
        return null;
    }
}
