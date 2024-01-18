package me.jishuna.jishlib.config.adapter.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class FurnaceRecipeAdapter implements TypeAdapter<ConfigurationSection, FurnaceRecipe> {
    private static final String INPUT = "input";
    private static final String EXPERIENCE = "experience";
    private static final String AMOUNT = "amount";
    private static final String OUTPUT = "output";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String COOKING_TIME = "cooking-time";

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<FurnaceRecipe> getRuntimeType() {
        return FurnaceRecipe.class;
    }

    @Override
    public FurnaceRecipe read(ConfigurationSection section) {
        NamespacedKey key = ConfigAPI.getAdapter(NamespacedKey.class).read(section.get(NAME));
        Material output = ConfigAPI.getAdapter(Material.class).read(section.get(OUTPUT));
        int amount = NumberUtils.clamp(section.getInt(AMOUNT), 1, 64);

        RecipeChoice input = ConfigAPI.getAdapter(RecipeChoice.class).read(section.get(INPUT));
        int cookingTime = NumberUtils.clamp(section.getInt(COOKING_TIME), 0, Integer.MAX_VALUE);
        float experience = (float) NumberUtils.clamp(section.getDouble(EXPERIENCE), 0, Float.MAX_VALUE);

        ItemStack item = new ItemStack(output, amount);

        return new FurnaceRecipe(key, item, input, experience, cookingTime);
    }

    @Override
    public ConfigurationSection write(FurnaceRecipe value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        ConfigAPI.setIfAbsent(existing, TYPE, RecipeType.FURNACE::name);
        ConfigAPI.setIfAbsent(existing, NAME, () -> value.getKey().toString());
        ConfigAPI.setIfAbsent(existing, OUTPUT, () -> value.getResult().getType().getKey().toString());
        ConfigAPI.setIfAbsent(existing, AMOUNT, () -> value.getResult().getAmount());
        ConfigAPI.setIfAbsent(existing, COOKING_TIME, value::getCookingTime);
        ConfigAPI.setIfAbsent(existing, EXPERIENCE, value::getExperience);
        ConfigAPI.setIfAbsent(existing, INPUT, () -> ConfigAPI.getAdapter(RecipeChoice.class).write(value.getInputChoice(), null, replace));

        return existing;
    }
}
