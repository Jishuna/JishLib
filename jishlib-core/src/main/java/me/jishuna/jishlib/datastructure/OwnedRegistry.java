package me.jishuna.jishlib.datastructure;

import org.bukkit.plugin.Plugin;

public class OwnedRegistry<K, V> extends Registry<K, V> {
    private final Plugin owner;

    public OwnedRegistry(Plugin owner) {
        this.owner = owner;
    }

    public Plugin getOwner() {
        return this.owner;
    }
}
