package me.jishuna.jishlib.pdc;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ListType<P, C> implements PersistentDataType<PersistentDataContainer, List<C>> {
    private final PersistentDataType<P, C> innerType;

    public ListType(PersistentDataType<P, C> innerType) {
        this.innerType = innerType;
    }

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<List<C>> getComplexType() {
        return (Class<List<C>>) (Object) List.class;
    }

    @Override
    public PersistentDataContainer toPrimitive(List<C> complex, PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        for (int i = 0; i < complex.size(); i++) {
            C entry = complex.get(i);
            container.set(new NamespacedKey("#", Integer.toString(i)), this.innerType, entry);
        }

        return container;
    }

    @Override
    public List<C> fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        List<C> list = new ArrayList<>();
        for (NamespacedKey key : primitive.getKeys()) {
            list.add(primitive.get(key, this.innerType));
        }

        return list;
    }
}
