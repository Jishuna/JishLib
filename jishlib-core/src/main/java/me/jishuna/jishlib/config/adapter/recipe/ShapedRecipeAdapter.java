package me.jishuna.jishlib.config.adapter.recipe;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class ShapedRecipeAdapter implements TypeAdapter<ConfigurationSection, ShapedRecipe> {
    private static final String INGREDIENTS = "ingredients";
    private static final String SHAPE = "shape";
    private static final String AMOUNT = "amount";
    private static final String OUTPUT = "output";
    private static final String NAME = "name";
    private static final String TYPE = "type";

    @SuppressWarnings("rawtypes")
    private final ConfigType<Map> ingredientMapType = new ConfigType<>(Map.class, List.of(new ConfigType<>(char.class), new ConfigType<>(RecipeChoice.class)));

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<ShapedRecipe> getRuntimeType() {
        return ShapedRecipe.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShapedRecipe read(ConfigurationSection section) {
        NamespacedKey key = ConfigAPI.getAdapter(NamespacedKey.class).read(section.get(NAME));
        Material output = ConfigAPI.getAdapter(Material.class).read(section.get(OUTPUT));
        int amount = NumberUtils.clamp(section.getInt(AMOUNT), 1, 64);

        ItemStack item = new ItemStack(output, amount);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        List<?> list = section.getList(SHAPE);
        recipe.shape(list.stream().map(Object::toString).toArray(String[]::new));

        Map<Character, RecipeChoice> ingredients = ConfigAPI.getAdapter(this.ingredientMapType).read(section.get(INGREDIENTS));

        for (Entry<Character, RecipeChoice> entry : ingredients.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue());
        }

        return recipe;
    }

    @Override
    public ConfigurationSection write(ShapedRecipe value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        ConfigAPI.setIfAbsent(existing, TYPE, RecipeType.SHAPED::name);
        ConfigAPI.setIfAbsent(existing, NAME, () -> value.getKey().toString());
        ConfigAPI.setIfAbsent(existing, OUTPUT, () -> value.getResult().getType().getKey().toString());
        ConfigAPI.setIfAbsent(existing, AMOUNT, () -> value.getResult().getAmount());
        ConfigAPI.setIfAbsent(existing, SHAPE, value::getShape);
        ConfigAPI.setIfAbsent(existing, INGREDIENTS, () -> ConfigAPI.getAdapter(this.ingredientMapType).write(value.getChoiceMap(), null, replace));

        return existing;
    }

}
