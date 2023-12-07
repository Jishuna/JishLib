package me.jishuna.jishlib.config;

import com.google.common.base.Preconditions;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.adapter.CollectionAdapter;
import me.jishuna.jishlib.config.adapter.EnumAdapter;
import me.jishuna.jishlib.config.adapter.MapAdapter;
import me.jishuna.jishlib.config.adapter.MaterialAdapter;
import me.jishuna.jishlib.config.adapter.NamespacedKeyAdapter;
import me.jishuna.jishlib.config.adapter.PrimitiveAdapter;
import me.jishuna.jishlib.config.adapter.StringAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapterString;
import me.jishuna.jishlib.config.adapter.WeightedRandomAdapter;
import me.jishuna.jishlib.config.adapter.recipe.FurnaceRecipeAdapter;
import me.jishuna.jishlib.config.adapter.recipe.RecipeAdapter;
import me.jishuna.jishlib.config.adapter.recipe.RecipeChoiceAdapter;
import me.jishuna.jishlib.config.adapter.recipe.ShapedRecipeAdapter;
import me.jishuna.jishlib.config.adapter.recipe.ShapelessRecipeAdapter;
import me.jishuna.jishlib.config.reloadable.ReloadableClass;
import me.jishuna.jishlib.config.reloadable.ReloadableObject;
import me.jishuna.jishlib.datastructure.WeightedRandom;

public final class ConfigApi {
    private static ConfigurationManager manager;

    public static void initialize() {
        Preconditions.checkArgument(manager == null, "ConfigApi already initialized!");
        Preconditions.checkArgument(JishLib.isInitialized(), "JishLib must be initialized first!");

        manager = new ConfigurationManager();
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
            getInstance().adapters.put(type, adapter);
        }

        return (TypeAdapter<S, R>) adapter;
    }

    private static TypeAdapter<?, ?> createAdapter(ConfigType<?> type) {
        if (Enum.class.isAssignableFrom(type.getType())) {
            return new EnumAdapter<>(type.getType());
        }
        if (Collection.class.isAssignableFrom(type.getType())) {
            return new CollectionAdapter<>(type);
        }

        if (Map.class.isAssignableFrom(type.getType())) {
            return new MapAdapter<>(type);
        }

        if (WeightedRandom.class.isAssignableFrom(type.getType())) {
            return new WeightedRandomAdapter<>(type);
        }

        return null;
    }

    private static ConfigurationManager getInstance() {
        if (manager == null) {
            throw new IllegalStateException("ConfigApi not initialized!");
        }
        return manager;
    }

    private ConfigApi() {
    }

    static final class ConfigurationManager {
        private final Map<ConfigType<?>, TypeAdapter<?, ?>> adapters = new HashMap<>();

        public ConfigurationManager() {
            registerTypeAdapter(long.class, new PrimitiveAdapter<>(Long.class, Long::parseLong));
            registerTypeAdapter(Long.class, new PrimitiveAdapter<>(Long.class, Long::parseLong));

            registerTypeAdapter(int.class, new PrimitiveAdapter<>(Integer.class, Integer::parseInt));
            registerTypeAdapter(Integer.class, new PrimitiveAdapter<>(Integer.class,Integer::parseInt));

            registerTypeAdapter(double.class, new PrimitiveAdapter<>(Double.class, Double::parseDouble));
            registerTypeAdapter(Double.class, new PrimitiveAdapter<>(Double.class, Double::parseDouble));

            registerTypeAdapter(float.class, new PrimitiveAdapter<>(Float.class, Float::parseFloat));
            registerTypeAdapter(Float.class, new PrimitiveAdapter<>(Float.class, Float::parseFloat));

            registerTypeAdapter(boolean.class, new PrimitiveAdapter<>(Boolean.class, Boolean::parseBoolean));
            registerTypeAdapter(Boolean.class, new PrimitiveAdapter<>(Boolean.class,Boolean::parseBoolean));

            registerTypeAdapter(char.class, new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));
            registerTypeAdapter(Character.class, new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));

            registerTypeAdapter(String.class, new StringAdapter());
            registerTypeAdapter(NamespacedKey.class, new NamespacedKeyAdapter());
            registerTypeAdapter(Material.class, new MaterialAdapter());

            registerTypeAdapter(RecipeChoice.class, new RecipeChoiceAdapter());
            registerTypeAdapter(Recipe.class, new RecipeAdapter());
            registerTypeAdapter(ShapedRecipe.class, new ShapedRecipeAdapter());
            registerTypeAdapter(ShapelessRecipe.class, new ShapelessRecipeAdapter());
            registerTypeAdapter(FurnaceRecipe.class, new FurnaceRecipeAdapter());
        }

        public <S, R> void registerTypeAdapter(Class<R> clazz, TypeAdapter<S, R> adapter) {
            this.adapters.put(new ConfigType<>(clazz), adapter);
        }
    }
}
