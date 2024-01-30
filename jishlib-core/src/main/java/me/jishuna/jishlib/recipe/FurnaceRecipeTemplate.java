package me.jishuna.jishlib.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.adapter.recipe.RecipeType;
import me.jishuna.jishlib.util.NumberUtils;

public class FurnaceRecipeTemplate extends RecipeTemplate {
    private final RecipeChoice input;
    private final float experience;
    private final int cookingTime;

    public FurnaceRecipeTemplate(RecipeChoice input, float experience, int cookingTime) {
        super(RecipeType.FURNACE);
        this.input = input;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    public static FurnaceRecipeTemplate read(ConfigurationSection section) {
        RecipeChoice input = ConfigAPI.getAdapter(RecipeChoice.class).read(section.get("input"));
        int cookingTime = NumberUtils.clamp(section.getInt("cooking-time"), 0, Integer.MAX_VALUE);
        float experience = (float) NumberUtils.clamp(section.getDouble("experience"), 0, Float.MAX_VALUE);

        return new FurnaceRecipeTemplate(input, experience, cookingTime);
    }

    @Override
    public void write(ConfigurationSection section, boolean replace) {
        super.write(section, replace);

        ConfigAPI.setIfAbsent(section, "cooking-time", () -> this.cookingTime);
        ConfigAPI.setIfAbsent(section, "experience", () -> this.experience);
        ConfigAPI.setIfAbsent(section, "input", () -> ConfigAPI.getAdapter(RecipeChoice.class).write(this.input, null, replace));
    }

    @Override
    public Recipe createRecipe(NamespacedKey key, ItemStack output) {
        return new FurnaceRecipe(key, output, this.input, this.experience, this.cookingTime);
    }
}
