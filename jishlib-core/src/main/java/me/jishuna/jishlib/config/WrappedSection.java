package me.jishuna.jishlib.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

public class WrappedSection extends MemorySection {

    public WrappedSection(ConfigurationSection parent) {
        super(parent, "");
    }
}
