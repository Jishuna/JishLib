package me.jishuna.jishlib.config;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.util.Vector;
import me.jishuna.jishlib.config.adapter.CollectionAdapter;
import me.jishuna.jishlib.config.adapter.ColorAdapter;
import me.jishuna.jishlib.config.adapter.ConfigSerializableAdapter;
import me.jishuna.jishlib.config.adapter.EntityIdentifierAdapter;
import me.jishuna.jishlib.config.adapter.EnumAdapter;
import me.jishuna.jishlib.config.adapter.ItemStackAdapter;
import me.jishuna.jishlib.config.adapter.MapAdapter;
import me.jishuna.jishlib.config.adapter.MaterialAdapter;
import me.jishuna.jishlib.config.adapter.NamespacedKeyAdapter;
import me.jishuna.jishlib.config.adapter.NativeAdapter;
import me.jishuna.jishlib.config.adapter.PrimitiveAdapter;
import me.jishuna.jishlib.config.adapter.StringAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapterString;
import me.jishuna.jishlib.config.adapter.VectorAdapter;
import me.jishuna.jishlib.config.adapter.WeightedRandomAdapter;
import me.jishuna.jishlib.config.adapter.recipe.FurnaceRecipeAdapter;
import me.jishuna.jishlib.config.adapter.recipe.RecipeAdapter;
import me.jishuna.jishlib.config.adapter.recipe.RecipeChoiceAdapter;
import me.jishuna.jishlib.config.adapter.recipe.RecipeTemplateAdapter;
import me.jishuna.jishlib.config.adapter.recipe.ShapedRecipeAdapter;
import me.jishuna.jishlib.config.adapter.recipe.ShapelessRecipeAdapter;
import me.jishuna.jishlib.config.reloadable.ReloadableClass;
import me.jishuna.jishlib.config.reloadable.ReloadableObject;
import me.jishuna.jishlib.datastructure.WeightedRandom;
import me.jishuna.jishlib.entity.EntityIdentifier;
import me.jishuna.jishlib.recipe.RecipeTemplate;

public final class ConfigAPI {
    private static ConfigurationManager manager;

    public static void setIfAbsent(ConfigurationSection section, String path, Supplier<Object> value) {
        if (!section.isSet(path)) {
            section.set(path, value.get());
        }
    }

    public static <S, R> void registerTypeAdapter(Class<R> clazz, TypeAdapter<S, R> adapter) {
        getInstance().adapters.put(new ConfigType<>(clazz), adapter);
    }

    public static <T> ReloadableObject<T> createReloadable(File file, T target) {
        return new ReloadableObject<>(file, target);
    }

    public static <T> ReloadableClass<T> createStaticReloadable(File file, Class<T> target) {
        return new ReloadableClass<>(file, target);
    }

    public static <S, R> TypeAdapterString<S, R> getStringAdapter(Class<R> clazz) {
        return getStringAdapter(new ConfigType<>(clazz));
    }

    @SuppressWarnings("unchecked")
    public static <S, R> TypeAdapterString<S, R> getStringAdapter(ConfigType<R> type) {
        TypeAdapter<?, R> adapter = getAdapter(type);

        if (adapter instanceof TypeAdapterString<?, ?> stringAdapter) {
            return (TypeAdapterString<S, R>) stringAdapter;
        }

        return null;
    }

    public static <S, R> TypeAdapter<S, R> getAdapter(Class<R> clazz) {
        return getAdapter(new ConfigType<>(clazz));
    }

    @SuppressWarnings("unchecked")
    public static <S, R> TypeAdapter<S, R> getAdapter(ConfigType<R> type) {
        TypeAdapter<?, ?> adapter = getInstance().adapters.get(type);
        if (adapter == null) {
            adapter = createAdapter(type);
            getInstance().registerTypeAdapter(type, adapter);
        }

        return (TypeAdapter<S, R>) adapter;
    }

    private static TypeAdapter<?, ?> createAdapter(ConfigType<?> type) {
        if (ConfigSerializable.class.isAssignableFrom(type.getType())) {
            return new ConfigSerializableAdapter<>(type);
        }

        if (WeightedRandom.class.isAssignableFrom(type.getType())) {
            return new WeightedRandomAdapter<>(type);
        }

        if (Enum.class.isAssignableFrom(type.getType())) {
            return new EnumAdapter<>(type.getType());
        }

        if (Collection.class.isAssignableFrom(type.getType())) {
            return new CollectionAdapter<>(type);
        }

        if (Map.class.isAssignableFrom(type.getType())) {
            return new MapAdapter<>(type);
        }

        return new NativeAdapter<>(type.getType());
    }

    private static ConfigurationManager getInstance() {
        if (manager == null) {
            manager = new ConfigurationManager();
        }
        return manager;
    }

    private ConfigAPI() {
    }

    static final class ConfigurationManager {
        private final Map<ConfigType<?>, TypeAdapter<?, ?>> adapters = new HashMap<>();

        public ConfigurationManager() {
            registerTypeAdapter(long.class, new PrimitiveAdapter<>(Long.class, Long::parseLong));
            registerTypeAdapter(Long.class, new PrimitiveAdapter<>(Long.class, Long::parseLong));

            registerTypeAdapter(int.class, new PrimitiveAdapter<>(Integer.class, Integer::parseInt));
            registerTypeAdapter(Integer.class, new PrimitiveAdapter<>(Integer.class, Integer::parseInt));

            registerTypeAdapter(double.class, new PrimitiveAdapter<>(Double.class, Double::parseDouble));
            registerTypeAdapter(Double.class, new PrimitiveAdapter<>(Double.class, Double::parseDouble));

            registerTypeAdapter(float.class, new PrimitiveAdapter<>(Float.class, Float::parseFloat));
            registerTypeAdapter(Float.class, new PrimitiveAdapter<>(Float.class, Float::parseFloat));

            registerTypeAdapter(boolean.class, new PrimitiveAdapter<>(Boolean.class, Boolean::parseBoolean));
            registerTypeAdapter(Boolean.class, new PrimitiveAdapter<>(Boolean.class, Boolean::parseBoolean));

            registerTypeAdapter(char.class, new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));
            registerTypeAdapter(Character.class, new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));

            registerTypeAdapter(String.class, new StringAdapter());
            registerTypeAdapter(NamespacedKey.class, new NamespacedKeyAdapter());
            registerTypeAdapter(Material.class, new MaterialAdapter());
            registerTypeAdapter(Color.class, new ColorAdapter());
            registerTypeAdapter(EntityIdentifier.class, new EntityIdentifierAdapter());
            registerTypeAdapter(Vector.class, new VectorAdapter());
            registerTypeAdapter(ItemStack.class, new ItemStackAdapter());

            registerTypeAdapter(RecipeChoice.class, new RecipeChoiceAdapter());
            registerTypeAdapter(Recipe.class, new RecipeAdapter());
            registerTypeAdapter(ShapedRecipe.class, new ShapedRecipeAdapter());
            registerTypeAdapter(ShapelessRecipe.class, new ShapelessRecipeAdapter());
            registerTypeAdapter(FurnaceRecipe.class, new FurnaceRecipeAdapter());

            registerTypeAdapter(RecipeTemplate.class, new RecipeTemplateAdapter());
        }

        protected <R> void registerTypeAdapter(Class<R> clazz, TypeAdapter<?, ?> adapter) {
            registerTypeAdapter(new ConfigType<>(clazz), adapter);
        }

        protected <R> void registerTypeAdapter(ConfigType<R> type, TypeAdapter<?, ?> adapter) {
            this.adapters.put(type, adapter);
        }
    }
}
