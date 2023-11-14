package me.jishuna.jishlib.enums;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Dye {
    WHITE(ColoredItems.WHITE, DyeColor.WHITE),
    LIGHT_GRAY(ColoredItems.LIGHT_GRAY, DyeColor.LIGHT_GRAY),
    GRAY(ColoredItems.GRAY, DyeColor.GRAY),
    BLACK(ColoredItems.BLACK, DyeColor.BLACK),
    BROWN(ColoredItems.BROWN, DyeColor.BROWN),
    RED(ColoredItems.RED, DyeColor.RED),
    ORANGE(ColoredItems.ORANGE, DyeColor.ORANGE),
    YELLOW(ColoredItems.YELLOW, DyeColor.YELLOW),
    LIME(ColoredItems.LIME, DyeColor.LIME),
    GREEN(ColoredItems.GREEN, DyeColor.GREEN),
    CYAN(ColoredItems.CYAN, DyeColor.CYAN),
    LIGHT_BLUE(ColoredItems.LIGHT_BLUE, DyeColor.LIGHT_BLUE),
    BLUE(ColoredItems.BLUE, DyeColor.BLUE),
    PURPLE(ColoredItems.PURPLE, DyeColor.PURPLE),
    MAGENTA(ColoredItems.MAGENTA, DyeColor.MAGENTA),
    PINK(ColoredItems.PINK, DyeColor.PINK);

    private static final Map<DyeColor, Dye> byDyeColor = new EnumMap<>(DyeColor.class);
    private static final Map<Material, Dye> byMaterial = new HashMap<>();

    static {
        for (Dye dye : Dye.values()) {
            byDyeColor.put(dye.dyeColor, dye);

            for (Material material : dye.materials) {
                if (material != null) {
                    byMaterial.put(material, dye);
                }
            }
        }
    }

    private final Material[] materials;
    private final DyeColor dyeColor;
    private final ChatColor chatColor;

    private Dye(String[] keys, DyeColor dyeColor) {
        this.materials = buildMaterialArray(keys);
        this.dyeColor = dyeColor;
        this.chatColor = ChatColor.of(new java.awt.Color(dyeColor.getColor().asRGB()));
    }

    private Material[] buildMaterialArray(String[] keys) {
        Material[] materialArray = new Material[keys.length];

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            materialArray[i] = Material.matchMaterial(key);
        }

        return materialArray;
    }

    public static Dye fromDyeColor(DyeColor color) {
        if (color == null) {
            return null;
        }

        return byDyeColor.get(color);
    }

    public static Dye fromItem(ItemStack item) {
        if (item == null) {
            return null;
        }

        return byMaterial.get(item.getType());
    }

    public static Dye fromMaterial(Material material) {
        if (material == null) {
            return null;
        }

        return byMaterial.get(material);
    }

    public Color getColor() {
        return this.dyeColor.getColor();
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }

    public ChatColor getChatColor() {
        return this.chatColor;
    }

    public Material getDye() {
        return this.materials[0];
    }

    public Material getWool() {
        return this.materials[1];
    }

    public Material getTerracotta() {
        return this.materials[2];
    }

    public Material getConcretePowder() {
        return this.materials[3];
    }

    public Material getConcrete() {
        return this.materials[4];
    }

    public Material getStainedGlass() {
        return this.materials[5];
    }

    public Material getStainedGlassPane() {
        return this.materials[6];
    }

    public Material getGlazedTerracotta() {
        return this.materials[7];
    }

    public Material getBed() {
        return this.materials[8];
    }

    public Material getCarpet() {
        return this.materials[9];
    }

    public Material getShulkerBox() {
        return this.materials[10];
    }

    public Material getCandle() {
        return this.materials[11];
    }

    public Material getCandleCake() {
        return this.materials[12];
    }

    public Material getBanner() {
        return this.materials[13];
    }

    public Material getWallBanner() {
        return this.materials[14];
    }

}
