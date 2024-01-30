package me.jishuna.jishlib.recipe;

import java.util.List;
import java.util.Map;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.adapter.recipe.RecipeType;

public class ShapedRecipeTemplate extends RecipeTemplate {
    @SuppressWarnings("rawtypes")
    private static final ConfigType<Map> ingredientMapType = new ConfigType<>(Map.class, List.of(new ConfigType<>(char.class), new ConfigType<>(RecipeChoice.class)));

    private final String[] shape;
    private final Map<Character, RecipeChoice> choiceMap;

    public ShapedRecipeTemplate(String[] shape, Map<Character, RecipeChoice> ingredientMap) {
        super(RecipeType.SHAPED);
        this.shape = shape;
        this.choiceMap = ingredientMap;
    }

    @SuppressWarnings("unchecked")
    public static ShapedRecipeTemplate read(ConfigurationSection section) {
        List<?> list = section.getList("shape");
        String[] shape = list.stream().map(Object::toString).toArray(String[]::new);
        Map<Character, RecipeChoice> ingredients = ConfigAPI.getAdapter(ingredientMapType).read(section.get("ingredients"));

        return new ShapedRecipeTemplate(shape, ingredients);
    }

    @Override
    public void write(ConfigurationSection section, boolean replace) {
        super.write(section, replace);

        ConfigAPI.setIfAbsent(section, "shape", () -> this.shape);
        ConfigAPI.setIfAbsent(section, "ingredients", () -> ConfigAPI.getAdapter(ingredientMapType).write(this.choiceMap, null, replace));
    }

    @Override
    public Recipe createRecipe(NamespacedKey key, ItemStack output) {
        ShapedRecipe recipe = new ShapedRecipe(key, output);
        recipe.shape(this.shape);
        this.choiceMap.forEach(recipe::setIngredient);

        return recipe;
    }
}
