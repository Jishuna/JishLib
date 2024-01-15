package me.jishuna.jishlib.config.adapter.recipe;

import java.util.Map;
import org.bukkit.inventory.Recipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class RecipeAdapter implements TypeAdapter<Map<String, Object>, Recipe> {

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @Override
    public Class<Recipe> getRuntimeType() {
        return Recipe.class;
    }

    @Override
    public Recipe read(Map<String, Object> value) {
        RecipeType type = ConfigAPI.getAdapter(RecipeType.class).read(value.get("type"));

        return ConfigAPI.getAdapter(type.getRecipeClass()).read(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> write(Recipe value, Map<String, Object> existing, boolean replace) {
        TypeAdapter<Object, Recipe> adapter = (TypeAdapter<Object, Recipe>) ConfigAPI.getAdapter(value.getClass());

        return (Map<String, Object>) adapter.write(value, existing, replace);
    }
}
