package me.jishuna.jishlib.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

public class DummySection extends MemorySection {

    public DummySection(ConfigurationSection parent) {
        super(parent, "");
    }
}
