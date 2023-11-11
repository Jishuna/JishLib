package me.jishuna.jishlib.item;

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import org.bukkit.Material;

public class ItemTypes {
    public static final ImmutableSet<Material> PICKAXES = Arrays
            .stream(Material.values())
            .filter(Material::isItem)
            .filter(mat -> mat.name().endsWith("_PICKAXE"))
            .collect(ImmutableSet.toImmutableSet());
    public static final ImmutableSet<Material> AXES = Arrays
            .stream(Material.values())
            .filter(Material::isItem)
            .filter(mat -> mat.name().endsWith("_AXE"))
            .collect(ImmutableSet.toImmutableSet());
    public static final ImmutableSet<Material> SHOVELS = Arrays
            .stream(Material.values())
            .filter(Material::isItem)
            .filter(mat -> mat.name().endsWith("_SHOVEL"))
            .collect(ImmutableSet.toImmutableSet());
    public static final ImmutableSet<Material> SWORDS = Arrays
            .stream(Material.values())
            .filter(Material::isItem)
            .filter(mat -> mat.name().endsWith("_SWORD"))
            .collect(ImmutableSet.toImmutableSet());
    public static final ImmutableSet<Material> HOES = Arrays
            .stream(Material.values())
            .filter(Material::isItem)
            .filter(mat -> mat.name().endsWith("_HOE"))
            .collect(ImmutableSet.toImmutableSet());
}
