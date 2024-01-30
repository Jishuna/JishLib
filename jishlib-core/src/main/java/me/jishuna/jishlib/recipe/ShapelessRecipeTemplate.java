package me.jishuna.jishlib.recipe;

import java.util.List;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.adapter.recipe.RecipeType;

public class ShapelessRecipeTemplate extends RecipeTemplate {
    @SuppressWarnings("rawtypes")
    private static final ConfigType<List> ingredientListType = new ConfigType<>(List.class, List.of(new ConfigType<>(RecipeChoice.class)));

    private final List<RecipeChoice> choices;

    public ShapelessRecipeTemplate(List<RecipeChoice> choices) {
        super(RecipeType.SHAPELESS);
        this.choices = choices;
    }

    @SuppressWarnings("unchecked")
    public static ShapelessRecipeTemplate read(ConfigurationSection section) {
        List<RecipeChoice> choices = ConfigAPI.getAdapter(ingredientListType).read(section.get("ingredients"));

        return new ShapelessRecipeTemplate(choices);
    }

    @Override
    public void write(ConfigurationSection section, boolean replace) {
        super.write(section, replace);

        ConfigAPI.setIfAbsent(section, "ingredients", () -> ConfigAPI.getAdapter(ingredientListType).write(this.choices, null, replace));
    }

    @Override
    public Recipe createRecipe(NamespacedKey key, ItemStack output) {
        ShapelessRecipe recipe = new ShapelessRecipe(key, output);
        this.choices.forEach(recipe::addIngredient);

        return recipe;
    }
}
