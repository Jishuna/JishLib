package me.jishuna.jishlib.config.adapter.recipe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class ShapedRecipeAdapter implements TypeAdapter<Map<String, Object>, ShapedRecipe> {
    @SuppressWarnings("rawtypes")
    ConfigType<Map> ingredientMapType = new ConfigType<>(Map.class, List.of(new ConfigType<>(char.class), new ConfigType<>(RecipeChoice.class)));

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @Override
    public Class<ShapedRecipe> getRuntimeType() {
        return ShapedRecipe.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShapedRecipe read(Map<String, Object> value) {
        NamespacedKey key = ConfigAPI.getAdapter(NamespacedKey.class).read(value.get("name"));
        Material output = ConfigAPI.getAdapter(Material.class).read(value.get("output"));
        int amount = NumberUtils.clamp(ConfigAPI.getAdapter(int.class).read(value.get("amount")), 1, 64);

        ItemStack item = new ItemStack(output, amount);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        List<?> list = (List<?>) value.get("shape");
        recipe.shape(list.stream().map(Object::toString).toArray(String[]::new));

        Map<Character, RecipeChoice> ingredients = ConfigAPI.getAdapter(this.ingredientMapType).read(readIngredients(value));

        for (Entry<Character, RecipeChoice> entry : ingredients.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue());
        }

        return recipe;
    }

    @Override
    public Map<String, Object> write(ShapedRecipe value, Map<String, Object> existing, boolean replace) {
        if (existing == null) {
            existing = new LinkedHashMap<>();
        }

        existing.putIfAbsent("type", RecipeType.SHAPED.name());
        existing.putIfAbsent("name", value.getKey().toString());
        existing.putIfAbsent("output", value.getResult().getType().getKey().toString());
        existing.putIfAbsent("amount", value.getResult().getAmount());
        existing.putIfAbsent("shape", value.getShape());

        Object ingredients = null;
        if (existing.containsKey("ingredients")) {
            ingredients = ConfigAPI.getAdapter(this.ingredientMapType).read(readIngredients(existing));
        }

        ingredients = ConfigAPI.getAdapter(this.ingredientMapType).write(value.getChoiceMap(), ingredients, replace);
        existing.putIfAbsent("ingredients", ingredients);

        return existing;
    }

    private Object readIngredients(Map<String, Object> map) {
        Object ingredientMap = map.get("ingredients");
        if (ingredientMap instanceof MemorySection section) {
            ingredientMap = section.getValues(false);
        }

        return ingredientMap;
    }

}
