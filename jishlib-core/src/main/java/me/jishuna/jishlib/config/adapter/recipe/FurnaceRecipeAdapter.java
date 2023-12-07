package me.jishuna.jishlib.config.adapter.recipe;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class FurnaceRecipeAdapter implements TypeAdapter<Map<String, Object>, FurnaceRecipe> {

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @Override
    public Class<FurnaceRecipe> getRuntimeType() {
        return FurnaceRecipe.class;
    }

    @Override
    public FurnaceRecipe read(Map<String, Object> value) {
        NamespacedKey key = ConfigApi.getAdapter(NamespacedKey.class).read(value.get("name"));
        Material output = ConfigApi.getAdapter(Material.class).read(value.get("output"));
        int amount = NumberUtils.clamp(ConfigApi.getAdapter(int.class).read(value.get("amount")), 1, 64);

        RecipeChoice input = ConfigApi.getAdapter(RecipeChoice.class).read(value.get("input"));
        int cookingTime = NumberUtils.clamp(ConfigApi.getAdapter(int.class).read(value.get("cooking-time")), 0, Integer.MAX_VALUE);
        float experience = (float) NumberUtils.clamp(ConfigApi.getAdapter(double.class).read(value.get("experience")), 0, Float.MAX_VALUE);

        ItemStack item = new ItemStack(output, amount);

        return new FurnaceRecipe(key, item, input, experience, cookingTime);
    }

    @Override
    public Map<String, Object> write(FurnaceRecipe value, Map<String, Object> existing, boolean replace) {
        if (existing == null) {
            existing = new LinkedHashMap<>();
        }

        existing.put("type", RecipeType.FURNACE.name());
        existing.put("name", value.getKey().toString());
        existing.put("output", value.getResult().getType().getKey().toString());
        existing.put("amount", value.getResult().getAmount());
        existing.put("cooking-time", value.getCookingTime());
        existing.put("experience", value.getExperience());

        Object input = null;
        if (existing.containsKey("input")) {
            input = ConfigApi.getAdapter(RecipeChoice.class).read(existing.get("input"));
        }
        input = ConfigApi.getAdapter(RecipeChoice.class).write(value.getInputChoice(), input, replace);

        existing.put("input", input);

        return existing;
    }
}
