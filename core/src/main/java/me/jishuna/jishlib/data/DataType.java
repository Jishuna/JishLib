package me.jishuna.jishlib.data;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataType<T> {

    private Class<T> clazz;
    private List<DataType<?>> componentTypes;

    public static DataType<?> create(Type type) {
        if (type instanceof Class) {
            return new DataType<>((Class<?>) type, new ArrayList<>());
        }
        return create(type.getTypeName());
    }

    public static DataType<?> get(Field field) {
        return create(field.getGenericType());
    }

    private static DataType<?> create(String typeName) {
        try {
            int ind = typeName.indexOf('<');
            if (ind == -1) {
                return new DataType<>(Class.forName(typeName));
            }
            Class<?> clazz = Class.forName(typeName.substring(0, ind));
            List<DataType<?>> componentTypes = splitOnComma(typeName, ind + 1, typeName.length() - 1)
                    .stream()
                    .map(DataType::create)
                    .collect(Collectors.toList());
            return new DataType<>(clazz, componentTypes);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("All parameter types for config must be known at compiletime", e);
        }
    }

    private static List<String> splitOnComma(String str, int start, int end) {
        int depth = 0;
        StringBuilder current = new StringBuilder();
        List<String> split = new ArrayList<>();
        for (int i = start; i < end; i++) {
            char c = str.charAt(i);
            switch (c) {
            case '<':
                depth++;
                break;
            case '>':
                depth--;
                break;
            case ',':
                if (depth != 0) {
                    break;
                }
                split.add(current.toString().trim());
                current = new StringBuilder();
                continue;
            }
            current.append(c);
        }
        String last = current.toString().trim();
        if (last.length() != 0) {
            split.add(last);
        }
        return split;
    }

    public DataType(Class<T> clazz, List<DataType<?>> componentTypes) {
        this.clazz = clazz;
        this.componentTypes = componentTypes;
    }

    public DataType(Class<T> clazz) {
        this(clazz, new ArrayList<>());
    }

    public Class<T> getType() {
        return this.clazz;
    }

    public List<DataType<?>> getComponentTypes() {
        return this.componentTypes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.clazz, this.componentTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DataType)) {
            return false;
        }
        DataType<?> type = (DataType<?>) o;
        return type.clazz.equals(this.clazz) && type.componentTypes.equals(this.componentTypes);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder().append(this.clazz.getName());
        if (!this.componentTypes.isEmpty()) {
            str.append("<").append(this.componentTypes.stream().map(DataType::toString).collect(Collectors.joining(", "))).append(">");
        }
        return str.toString();
    }
}
