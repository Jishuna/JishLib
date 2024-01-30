package me.jishuna.jishlib.config.adapter.recipe;

import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.recipe.RecipeTemplate;

public class RecipeTemplateAdapter implements TypeAdapter<ConfigurationSection, RecipeTemplate> {

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<RecipeTemplate> getRuntimeType() {
        return RecipeTemplate.class;
    }

    @Override
    public RecipeTemplate read(ConfigurationSection section) {
        return RecipeTemplate.read(section);
    }

    @Override
    public ConfigurationSection write(RecipeTemplate value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        value.write(existing, replace);
        return existing;
    }
}
