package me.jishuna.jishlib.config.adapter.recipe;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class FurnaceRecipeAdapter implements TypeAdapter<FurnaceRecipe> {
    @SuppressWarnings("rawtypes")
    ConfigType<List> ingredientListType = new ConfigType<>(List.class, List.of(new ConfigType<>(RecipeChoice.class)));

    @SuppressWarnings("unchecked")
    @Override
    public FurnaceRecipe read(Object value) {
        Map<String, Object> map = (Map<String, Object>) value;

        NamespacedKey key = ConfigApi.getAdapter(NamespacedKey.class).read(map.get("name"));
        Material output = ConfigApi.getAdapter(Material.class).read(map.get("output"));
        int amount = NumberUtils.clamp(ConfigApi.getAdapter(int.class).read(map.get("amount")), 1, 64);

        RecipeChoice input = ConfigApi.getAdapter(RecipeChoice.class).read(map.get("input"));
        int cookingTime = NumberUtils.clamp(ConfigApi.getAdapter(int.class).read(map.get("cooking-time")), 0, Integer.MAX_VALUE);
        float experience = NumberUtils.clamp(ConfigApi.getAdapter(float.class).read(map.get("experience")), 0, Float.MAX_VALUE);

        ItemStack item = new ItemStack(output, amount);

        return new FurnaceRecipe(key, item, input, experience, cookingTime);
    }

    @Override
    public void write(ConfigurationSection config, String path, FurnaceRecipe value, boolean replace) {
        if (!replace && config.isSet(path)) {
            return;
        }

        ConfigurationSection section = config.createSection(path);
        section.set("type", RecipeType.FURNACE.name());
        section.set("name", value.getKey().toString());
        section.set("output", value.getResult().getType().getKey().toString());
        section.set("amount", value.getResult().getAmount());
        section.set("cooking-time", value.getCookingTime());
        section.set("experience", value.getExperience());

        ConfigApi.getAdapter(RecipeChoice.class).write(section, "input", value.getInputChoice(), true);
    }
}
