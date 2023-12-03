package me.jishuna.jishlib.config.adapter.recipe;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public enum RecipeType {
    SHAPED(ShapedRecipe.class), SHAPELESS(ShapelessRecipe.class), FURNACE(FurnaceRecipe.class);

    private final Class<? extends Recipe> clazz;

    private RecipeType(Class<? extends Recipe> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Recipe> getRecipeClass() {
        return this.clazz;
    }
}
