package me.jishuna.jishlib.config.adapter.recipe;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class RecipeAdapter implements TypeAdapter<ConfigurationSection, Recipe> {

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<Recipe> getRuntimeType() {
        return Recipe.class;
    }

    @Override
    public Recipe read(ConfigurationSection section) {
        RecipeType type = ConfigAPI.getAdapter(RecipeType.class).read(section.get("type"));

        return ConfigAPI.getAdapter(type.getRecipeClass()).read(section);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigurationSection write(Recipe value, ConfigurationSection existing, boolean replace) {
        TypeAdapter<Object, Recipe> adapter = (TypeAdapter<Object, Recipe>) ConfigAPI.getAdapter(value.getClass());

        return (ConfigurationSection) adapter.write(value, existing, replace);
    }
}
