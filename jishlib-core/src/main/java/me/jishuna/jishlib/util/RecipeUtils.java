package me.jishuna.jishlib.util;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public final class RecipeUtils {

    public static ShapedRecipe copyRecipe(ShapedRecipe original, ItemStack result) {
        return copyRecipe(original, original.getKey(), result);
    }

    public static ShapelessRecipe copyRecipe(ShapelessRecipe original, ItemStack result) {
        return copyRecipe(original, original.getKey(), result);
    }

    public static Recipe copyRecipe(Recipe original, ItemStack result) {
        if (!(original instanceof Keyed keyed)) {
            return original;
        }

        return copyRecipe(original, keyed.getKey(), result);
    }

    public static ShapedRecipe copyRecipe(ShapedRecipe original, NamespacedKey key, ItemStack result) {
        ShapedRecipe newRecipe = new ShapedRecipe(key, result);
        newRecipe.shape(original.getShape());
        original.getChoiceMap().forEach(newRecipe::setIngredient);
        newRecipe.setGroup(original.getGroup());

        return newRecipe;
    }

    public static ShapelessRecipe copyRecipe(ShapelessRecipe original, NamespacedKey key, ItemStack result) {
        ShapelessRecipe newRecipe = new ShapelessRecipe(key, result);
        original.getChoiceList().forEach(newRecipe::addIngredient);

        return newRecipe;
    }

    public static Recipe copyRecipe(Recipe original, NamespacedKey key, ItemStack result) {
        if (original instanceof ShapedRecipe recipe) {
            return copyRecipe(recipe, key, result);
        }

        if (original instanceof ShapelessRecipe recipe) {
            return copyRecipe(recipe, key, result);
        }

        return original;
    }

    private RecipeUtils() {
    }
}
