package me.jishuna.jishlib.config.adapter.recipe;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class RecipeChoiceAdapter implements TypeAdapter<Object, RecipeChoice> {
    @Override
    public Class<Object> getSavedType() {
        return Object.class;
    }

    @Override
    public Class<RecipeChoice> getRuntimeType() {
        return RecipeChoice.class;
    }

    @Override
    public RecipeChoice read(Object value) {
        if (!(value instanceof List<?> list)) {
            Material type = ConfigAPI.getStringAdapter(Material.class).fromString(value.toString());
            return type == null ? null : new MaterialChoice(type);
        }

        List<Material> materials = new ArrayList<>();
        for (Object entry : list) {
            materials.add(ConfigAPI.getStringAdapter(Material.class).fromString(entry.toString()));
        }

        return new MaterialChoice(materials);
    }

    @Override
    public Object write(RecipeChoice value, Object existing, boolean replace) {
        if (existing != null && !replace) {
            return existing;
        }

        if (value instanceof MaterialChoice choice) {
            List<Material> choices = choice.getChoices();

            if (choices.size() == 1) {
                return choices.get(0).getKey().toString();
            }
            return choices.stream().map(mat -> mat.getKey().toString()).toList();
        }

        return null;
    }
}
