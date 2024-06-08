package me.jishuna.jishlib.data.adapter;

import static me.jishuna.jishlib.data.adapter.DefaultAdapters.COMPONENT;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.DOUBLE;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.FLOAT;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.INT;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.ITEM_STACK;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.LONG;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.MATERIAL;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.NAMESPACE;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.data.DataType;
import me.jishuna.jishlib.data.object.DataObject;

public class TypeAdapterRegistry {
    private static TypeAdapterRegistry INSTANCE;

    public static <T extends DataObject<?>, R> TypeAdapter<T, R> getAdapter(Class<R> clazz) {
        return getAdapter(new DataType<>(clazz));
    }

    public static <T extends DataObject<?>, R> void register(Class<R> clazz, TypeAdapter<T, R> adapter) {
        getInstance().adapters.put(new DataType<>(clazz), adapter);
    }

    @SuppressWarnings("unchecked")
    public static <T extends DataObject<?>, R> TypeAdapter<T, R> getAdapter(DataType<R> type) {
        TypeAdapter<?, ?> adapter = getInstance().adapters.get(type);
        if (adapter == null) {
            adapter = createAdapter(type);
            getInstance().registerTypeAdapter(type, adapter);
        }

        return (TypeAdapter<T, R>) adapter;
    }

    private static TypeAdapter<?, ?> createAdapter(DataType<?> type) {
        if (Map.class.isAssignableFrom(type.getType())) {
            return new MapAdapter<>(type);
        }

        if (Collection.class.isAssignableFrom(type.getType())) {
            return new CollectionAdapter<>(type);
        }

        return null;
    }

    private static TypeAdapterRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TypeAdapterRegistry();
        }

        return INSTANCE;
    }

    private final Map<DataType<?>, TypeAdapter<?, ?>> adapters = new HashMap<>();

    private TypeAdapterRegistry() {
        registerTypeAdapter(int.class, INT);
        registerTypeAdapter(Integer.class, INT);

        registerTypeAdapter(long.class, LONG);
        registerTypeAdapter(Long.class, LONG);

        registerTypeAdapter(float.class, FLOAT);
        registerTypeAdapter(Float.class, FLOAT);

        registerTypeAdapter(double.class, DOUBLE);
        registerTypeAdapter(Double.class, DOUBLE);

        registerTypeAdapter(boolean.class, new BooleanAdapter());
        registerTypeAdapter(Boolean.class, new BooleanAdapter());

        registerTypeAdapter(char.class, new CharacterAdapter());
        registerTypeAdapter(Character.class, new CharacterAdapter());

        registerTypeAdapter(String.class, new StringAdapter());

        registerTypeAdapter(Material.class, MATERIAL);
        registerTypeAdapter(NamespacedKey.class, NAMESPACE);
        registerTypeAdapter(Component.class, COMPONENT);

        registerTypeAdapter(ItemStack.class, ITEM_STACK);
    }

    private <T> void registerTypeAdapter(Class<T> clazz, TypeAdapter<?, ?> adapter) {
        registerTypeAdapter(new DataType<>(clazz), adapter);
    }

    private <T> void registerTypeAdapter(DataType<T> type, TypeAdapter<?, ?> adapter) {
        this.adapters.put(type, adapter);
    }
}
