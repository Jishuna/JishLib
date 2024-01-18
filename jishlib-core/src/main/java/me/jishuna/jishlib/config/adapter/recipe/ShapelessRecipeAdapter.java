package me.jishuna.jishlib.config.adapter.recipe;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class ShapelessRecipeAdapter implements TypeAdapter<ConfigurationSection, ShapelessRecipe> {
    private static final String INGREDIENTS = "ingredients";
    private static final String AMOUNT = "amount";
    private static final String OUTPUT = "output";
    private static final String NAME = "name";
    private static final String TYPE = "type";

    @SuppressWarnings("rawtypes")
    private final ConfigType<List> ingredientListType = new ConfigType<>(List.class, List.of(new ConfigType<>(RecipeChoice.class)));

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<ShapelessRecipe> getRuntimeType() {
        return ShapelessRecipe.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShapelessRecipe read(ConfigurationSection section) {
        NamespacedKey key = ConfigAPI.getAdapter(NamespacedKey.class).read(section.get(NAME));
        Material output = ConfigAPI.getAdapter(Material.class).read(section.get(OUTPUT));
        int amount = NumberUtils.clamp(ConfigAPI.getAdapter(int.class).read(section.get(AMOUNT)), 1, 64);

        ItemStack item = new ItemStack(output, amount);

        ShapelessRecipe recipe = new ShapelessRecipe(key, item);

        List<RecipeChoice> choices = ConfigAPI.getAdapter(this.ingredientListType).read(section.get(INGREDIENTS));

        for (RecipeChoice choice : choices) {
            recipe.addIngredient(choice);
        }

        return recipe;
    }

    @Override
    public ConfigurationSection write(ShapelessRecipe value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        ConfigAPI.setIfAbsent(existing, TYPE, RecipeType.SHAPELESS::name);
        ConfigAPI.setIfAbsent(existing, NAME, () -> value.getKey().toString());
        ConfigAPI.setIfAbsent(existing, OUTPUT, () -> value.getResult().getType().getKey().toString());
        ConfigAPI.setIfAbsent(existing, AMOUNT, () -> value.getResult().getAmount());
        ConfigAPI.setIfAbsent(existing, INGREDIENTS, () -> ConfigAPI.getAdapter(this.ingredientListType).write(value.getChoiceList(), null, replace));

        return existing;
    }

}
