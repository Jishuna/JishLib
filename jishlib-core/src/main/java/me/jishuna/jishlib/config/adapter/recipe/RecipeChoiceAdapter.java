package me.jishuna.jishlib.config.adapter.recipe;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class RecipeChoiceAdapter implements TypeAdapter<RecipeChoice> {

    @SuppressWarnings("unchecked")
    @Override
    public RecipeChoice read(Object value) {
        List<Material> materials = new ArrayList<>();

        for (String type : (List<String>) value) {
            materials.add(ConfigApi.getStringAdapter(Material.class).fromString(type));
        }

        return new MaterialChoice(materials);
    }

    @Override
    public void write(ConfigurationSection config, String path, RecipeChoice value, boolean replace) {
        if (!replace && config.isSet(path)) {
            return;
        }

        if (value instanceof MaterialChoice choice) {
            config.set(path, choice.getChoices().stream().map(mat -> mat.getKey().toString()).toList());
        }
    }
}
