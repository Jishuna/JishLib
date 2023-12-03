package me.jishuna.jishlib.config.adapter.recipe;

import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class RecipeAdapter implements TypeAdapter<Recipe> {

    @SuppressWarnings("unchecked")
    @Override
    public Recipe read(Object value) {
        Map<String, Object> map = (Map<String, Object>) value;
        RecipeType type = ConfigApi.getAdapter(RecipeType.class).read(map.get("type"));

        return ConfigApi.getAdapter(type.getRecipeClass()).read(value);
    }

    @Override
    public void write(ConfigurationSection config, String path, Recipe value, boolean replace) {
        if (!replace && config.isSet(path)) {
            return;
        }

        if (value instanceof ShapedRecipe shaped) {
            ConfigApi.getAdapter(ShapedRecipe.class).write(config, path, shaped, replace);
        } else if (value instanceof ShapelessRecipe shapeless) {
            ConfigApi.getAdapter(ShapelessRecipe.class).write(config, path, shapeless, replace);
        }
    }
}
