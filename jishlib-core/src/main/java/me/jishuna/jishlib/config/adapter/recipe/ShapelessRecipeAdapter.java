package me.jishuna.jishlib.config.adapter.recipe;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.util.NumberUtils;

public class ShapelessRecipeAdapter implements TypeAdapter<ShapelessRecipe> {
    @SuppressWarnings("rawtypes")
    ConfigType<List> ingredientListType = new ConfigType<>(List.class, List.of(new ConfigType<>(RecipeChoice.class)));

    @SuppressWarnings("unchecked")
    @Override
    public ShapelessRecipe read(Object value) {
        Map<String, Object> map = (Map<String, Object>) value;

        NamespacedKey key = ConfigApi.getAdapter(NamespacedKey.class).read(map.get("name"));
        Material output = ConfigApi.getAdapter(Material.class).read(map.get("output"));
        int amount = NumberUtils.clamp(ConfigApi.getAdapter(int.class).read(map.get("amount")), 1, 64);

        ItemStack item = new ItemStack(output, amount);

        ShapelessRecipe recipe = new ShapelessRecipe(key, item);

        List<RecipeChoice> choices = ConfigApi.getAdapter(this.ingredientListType).read(map.get("ingredients"));

        for (RecipeChoice choice : choices) {
            recipe.addIngredient(choice);
        }

        return recipe;
    }

    @Override
    public void write(ConfigurationSection config, String path, ShapelessRecipe value, boolean replace) {
        if (!replace && config.isSet(path)) {
            return;
        }

        ConfigurationSection section = config.createSection(path);
        section.set("type", RecipeType.SHAPELESS.name());
        section.set("name", value.getKey().toString());
        section.set("output", value.getResult().getType().getKey().toString());
        section.set("amount", value.getResult().getAmount());

        ConfigApi.getAdapter(this.ingredientListType).write(section, "ingredients", value.getChoiceList(), true);
    }

}
