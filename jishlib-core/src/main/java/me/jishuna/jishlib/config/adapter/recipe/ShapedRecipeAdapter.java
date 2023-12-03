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
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class ShapedRecipeAdapter implements TypeAdapter<ShapedRecipe> {
    @SuppressWarnings("rawtypes")
    ConfigType<Map> ingredientMapType = new ConfigType<>(Map.class, List.of(new ConfigType<>(char.class), new ConfigType<>(RecipeChoice.class)));

    @SuppressWarnings("unchecked")
    @Override
    public ShapedRecipe read(Object value) {
        Map<String, Object> map = (Map<String, Object>) value;

        NamespacedKey key = ConfigApi.getAdapter(NamespacedKey.class).read(map.get("name"));
        Material output = ConfigApi.getAdapter(Material.class).read(map.get("output"));
        int amount = NumberUtils.clamp(ConfigApi.getAdapter(int.class).read(map.get("amount")), 1, 64);

        ItemStack item = new ItemStack(output, amount);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        List<?> list = (List<?>) map.get("shape");
        recipe.shape(list.stream().map(Object::toString).toArray(String[]::new));

        Map<Character, RecipeChoice> ingredients = ConfigApi.getAdapter(this.ingredientMapType).read(map.get("ingredients"));

        for (Entry<Character, RecipeChoice> entry : ingredients.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue());
        }

        return recipe;
    }

    @Override
    public void write(ConfigurationSection config, String path, ShapedRecipe value, boolean replace) {
        if (!replace && config.isSet(path)) {
            return;
        }

        ConfigurationSection section = config.createSection(path);
        section.set("type", RecipeType.SHAPED.name());
        section.set("name", value.getKey().toString());
        section.set("output", value.getResult().getType().getKey().toString());
        section.set("amount", value.getResult().getAmount());

        section.set("shape", value.getShape());
        ConfigApi.getAdapter(this.ingredientMapType).write(section, "ingredients", value.getChoiceMap(), true);
    }

}
