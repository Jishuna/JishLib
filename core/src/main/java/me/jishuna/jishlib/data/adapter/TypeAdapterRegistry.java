package me.jishuna.jishlib.data.adapter;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import me.jishuna.jishlib.data.DataType;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;

public class TypeAdapterRegistry {
    private static TypeAdapterRegistry INSTANCE;

    public static <T extends DataObject<?>, R> TypeAdapter<T, R> getAdapter(Class<R> clazz) {
        return getAdapter(new DataType<>(clazz));
    }

    public static <T extends DataObject<?>, R> void register(Class<R> clazz, TypeAdapter<T, R> adapter) {
        getInstance().adapters.put(new DataType<>(clazz), adapter);
    }

    @SuppressWarnings("unchecked")
    public static <T extends DataObject<?>, R> TypeAdapterString<R> getStringAdapter(DataType<R> type) {
        TypeAdapter<?, R> adapter = getAdapter(type);

        if (adapter instanceof TypeAdapterString<?> stringAdapter) {
            return (TypeAdapterString<R>) stringAdapter;
        }

        return null;
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
        registerTypeAdapter(int.class, new NumericAdapter<>(NumericDataObject::asInt));
        registerTypeAdapter(Integer.class, new NumericAdapter<>(NumericDataObject::asInt));

        registerTypeAdapter(long.class, new NumericAdapter<>(NumericDataObject::asLong));
        registerTypeAdapter(Long.class, new NumericAdapter<>(NumericDataObject::asLong));

        registerTypeAdapter(float.class, new NumericAdapter<>(NumericDataObject::asFloat));
        registerTypeAdapter(Float.class, new NumericAdapter<>(NumericDataObject::asFloat));

        registerTypeAdapter(double.class, new NumericAdapter<>(NumericDataObject::asDouble));
        registerTypeAdapter(Double.class, new NumericAdapter<>(NumericDataObject::asDouble));

        registerTypeAdapter(boolean.class, new BooleanAdapter());
        registerTypeAdapter(Boolean.class, new BooleanAdapter());

        registerTypeAdapter(char.class, new CharacterAdapter());
        registerTypeAdapter(Character.class, new CharacterAdapter());

        registerTypeAdapter(String.class, new StringAdapter());

        registerTypeAdapter(Material.class, new MaterialAdapter());
    }

    private <T> void registerTypeAdapter(Class<T> clazz, TypeAdapter<?, ?> adapter) {
        registerTypeAdapter(new DataType<>(clazz), adapter);
    }

    private <T> void registerTypeAdapter(DataType<T> type, TypeAdapter<?, ?> adapter) {
        this.adapters.put(type, adapter);
    }
}
