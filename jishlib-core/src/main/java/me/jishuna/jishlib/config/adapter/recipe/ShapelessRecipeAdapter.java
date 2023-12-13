package me.jishuna.jishlib.config.adapter.recipe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class ShapelessRecipeAdapter implements TypeAdapter<Map<String, Object>, ShapelessRecipe> {
    @SuppressWarnings("rawtypes")
    ConfigType<List> ingredientListType = new ConfigType<>(List.class, List.of(new ConfigType<>(RecipeChoice.class)));

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @Override
    public Class<ShapelessRecipe> getRuntimeType() {
        return ShapelessRecipe.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShapelessRecipe read(Map<String, Object> value) {
        NamespacedKey key = ConfigApi.getAdapter(NamespacedKey.class).read(value.get("name"));
        Material output = ConfigApi.getAdapter(Material.class).read(value.get("output"));
        int amount = NumberUtils.clamp(ConfigApi.getAdapter(int.class).read(value.get("amount")), 1, 64);

        ItemStack item = new ItemStack(output, amount);

        ShapelessRecipe recipe = new ShapelessRecipe(key, item);

        List<RecipeChoice> choices = ConfigApi.getAdapter(this.ingredientListType).read(value.get("ingredients"));

        for (RecipeChoice choice : choices) {
            recipe.addIngredient(choice);
        }

        return recipe;
    }

    @Override
    public Map<String, Object> write(ShapelessRecipe value, Map<String, Object> existing, boolean replace) {
        if (existing == null) {
            existing = new LinkedHashMap<>();
        }

        existing.putIfAbsent("type", RecipeType.SHAPELESS.name());
        existing.putIfAbsent("name", value.getKey().toString());
        existing.putIfAbsent("output", value.getResult().getType().getKey().toString());
        existing.putIfAbsent("amount", value.getResult().getAmount());

        Object ingredients = null;
        if (existing.containsKey("ingredients")) {
            ingredients = ConfigApi.getAdapter(this.ingredientListType).read(existing.get("ingredients"));
        }

        ingredients = ConfigApi.getAdapter(this.ingredientListType).write(value.getChoiceList(), ingredients, replace);
        existing.putIfAbsent("ingredients", ingredients);

        return existing;
    }

}
