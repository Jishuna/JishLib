package me.jishuna.jishlib.util;

import org.bukkit.NamespacedKey;

import java.util.Objects;

public class Key {
    public static final String MINECRAFT = "minecraft";

    private final String namespace;
    private final String value;
    private NamespacedKey bukkit;

    public static Key of(String namespace, String value) {
        return new Key(namespace, value);
    }

    public static Key minecraft(String value) {
        return new Key(MINECRAFT, value);
    }

    public static Key of(String string) {
        String[] components = string.split(":", 3);
        if (components.length > 2) {
            return null;
        }

        return new Key(components[0], components[1]);
    }

    private Key(String namespace, String value) {
        this.namespace = namespace;
        this.value = value;
    }

    public NamespacedKey toBukkit() {
        if (bukkit == null) {
            bukkit = new NamespacedKey(namespace, value);
        }

        return bukkit;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Key key)) return false;
        return Objects.equals(namespace, key.namespace) && Objects.equals(value, key.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, value);
    }

    @Override
    public String toString() {
        return namespace + ":" + value;
    }
}
