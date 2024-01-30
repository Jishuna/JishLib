package me.jishuna.jishlib.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.adapter.recipe.RecipeType;

public abstract class RecipeTemplate {
    private final RecipeType type;

    protected RecipeTemplate(RecipeType type) {
        this.type = type;
    }

    public void write(ConfigurationSection section, boolean replace) {
        ConfigAPI.setIfAbsent(section, "type", this.type::name);
    }

    public abstract Recipe createRecipe(NamespacedKey key, ItemStack output);

    public static RecipeTemplate read(ConfigurationSection section) {
        RecipeType type = ConfigAPI.getAdapter(RecipeType.class).read(section.get("type"));

        if (type == RecipeType.SHAPED) {
            return ShapedRecipeTemplate.read(section);
        }

        if (type == RecipeType.SHAPELESS) {
            return ShapelessRecipeTemplate.read(section);
        }

        if (type == RecipeType.FURNACE) {
            return FurnaceRecipeTemplate.read(section);
        }

        return null;
    }

    public static RecipeTemplate fromRecipe(Recipe rec) {
        if (rec instanceof ShapedRecipe recipe) {
            return new ShapedRecipeTemplate(recipe.getShape(), recipe.getChoiceMap());
        }

        if (rec instanceof ShapelessRecipe recipe) {
            return new ShapelessRecipeTemplate(recipe.getChoiceList());
        }

        if (rec instanceof FurnaceRecipe recipe) {
            return new FurnaceRecipeTemplate(recipe.getInputChoice(), recipe.getExperience(), recipe.getCookingTime());
        }

        return null;
    }
}
