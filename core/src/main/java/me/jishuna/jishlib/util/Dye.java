package me.jishuna.jishlib.util;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Dye {
    WHITE(DyeColor.WHITE, "white_dye", "white_wool", "white_terracotta", "white_concrete_powder", "white_concrete", "white_stained_glass", "white_stained_glass_pane", "white_glazed_terracotta", "white_bed", "white_carpet", "white_shulker_box", "white_candle", "white_candle_cake", "white_banner", "white_wall_banner"),
    LIGHT_GRAY(DyeColor.LIGHT_GRAY, "light_gray_dye", "light_gray_wool", "light_gray_terracotta", "light_gray_concrete_powder", "light_gray_concrete", "light_gray_stained_glass", "light_gray_stained_glass_pane", "light_gray_glazed_terracotta", "light_gray_bed", "light_gray_carpet", "light_gray_shulker_box", "light_gray_candle", "light_gray_candle_cake", "light_gray_banner", "light_gray_wall_banner"),
    GRAY(DyeColor.GRAY, "gray_dye", "gray_wool", "gray_terracotta", "gray_concrete_powder", "gray_concrete", "gray_stained_glass", "gray_stained_glass_pane", "gray_glazed_terracotta", "gray_bed", "gray_carpet", "gray_shulker_box", "gray_candle", "gray_candle_cake", "gray_banner", "gray_wall_banner"),
    BLACK(DyeColor.BLACK, "black_dye", "black_wool", "black_terracotta", "black_concrete_powder", "black_concrete", "black_stained_glass", "black_stained_glass_pane", "black_glazed_terracotta", "black_bed", "black_carpet", "black_shulker_box", "black_candle", "black_candle_cake", "black_banner", "black_wall_banner"),
    BROWN(DyeColor.BROWN, "brown_dye", "brown_wool", "brown_terracotta", "brown_concrete_powder", "brown_concrete", "brown_stained_glass", "brown_stained_glass_pane", "brown_glazed_terracotta", "brown_bed", "brown_carpet", "brown_shulker_box", "brown_candle", "brown_candle_cake", "brown_banner", "brown_wall_banner"),
    RED(DyeColor.RED, "red_dye", "red_wool", "red_terracotta", "red_concrete_powder", "red_concrete", "red_stained_glass", "red_stained_glass_pane", "red_glazed_terracotta", "red_bed", "red_carpet", "red_shulker_box", "red_candle", "red_candle_cake", "red_banner", "red_wall_banner"),
    ORANGE(DyeColor.ORANGE, "orange_dye", "orange_wool", "orange_terracotta", "orange_concrete_powder", "orange_concrete", "orange_stained_glass", "orange_stained_glass_pane", "orange_glazed_terracotta", "orange_bed", "orange_carpet", "orange_shulker_box", "orange_candle", "orange_candle_cake", "orange_banner", "orange_wall_banner"),
    YELLOW(DyeColor.YELLOW, "yellow_dye", "yellow_wool", "yellow_terracotta", "yellow_concrete_powder", "yellow_concrete", "yellow_stained_glass", "yellow_stained_glass_pane", "yellow_glazed_terracotta", "yellow_bed", "yellow_carpet", "yellow_shulker_box", "yellow_candle", "yellow_candle_cake", "yellow_banner", "yellow_wall_banner"),
    LIME(DyeColor.LIME, "lime_dye", "lime_wool", "lime_terracotta", "lime_concrete_powder", "lime_concrete", "lime_stained_glass", "lime_stained_glass_pane", "lime_glazed_terracotta", "lime_bed", "lime_carpet", "lime_shulker_box", "lime_candle", "lime_candle_cake", "lime_banner", "lime_wall_banner"),
    GREEN(DyeColor.GREEN, "green_dye", "green_wool", "green_terracotta", "green_concrete_powder", "green_concrete", "green_stained_glass", "green_stained_glass_pane", "green_glazed_terracotta", "green_bed", "green_carpet", "green_shulker_box", "green_candle", "green_candle_cake", "green_banner", "green_wall_banner"),
    CYAN(DyeColor.CYAN, "cyan_dye", "cyan_wool", "cyan_terracotta", "cyan_concrete_powder", "cyan_concrete", "cyan_stained_glass", "cyan_stained_glass_pane", "cyan_glazed_terracotta", "cyan_bed", "cyan_carpet", "cyan_shulker_box", "cyan_candle", "cyan_candle_cake", "cyan_banner", "cyan_wall_banner"),
    LIGHT_BLUE(DyeColor.LIGHT_BLUE, "light_blue_dye", "light_blue_wool", "light_blue_terracotta", "light_blue_concrete_powder", "light_blue_concrete", "light_blue_stained_glass", "light_blue_stained_glass_pane", "light_blue_glazed_terracotta", "light_blue_bed", "light_blue_carpet", "light_blue_shulker_box", "light_blue_candle", "light_blue_candle_cake", "light_blue_banner", "light_blue_wall_banner"),
    BLUE(DyeColor.BLUE, "blue_dye", "blue_wool", "blue_terracotta", "blue_concrete_powder", "blue_concrete", "blue_stained_glass", "blue_stained_glass_pane", "blue_glazed_terracotta", "blue_bed", "blue_carpet", "blue_shulker_box", "blue_candle", "blue_candle_cake", "blue_banner", "blue_wall_banner"),
    PURPLE(DyeColor.PURPLE, "purple_dye", "purple_wool", "purple_terracotta", "purple_concrete_powder", "purple_concrete", "purple_stained_glass", "purple_stained_glass_pane", "purple_glazed_terracotta", "purple_bed", "purple_carpet", "purple_shulker_box", "purple_candle", "purple_candle_cake", "purple_banner", "purple_wall_banner"),
    MAGENTA(DyeColor.MAGENTA, "magenta_dye", "magenta_wool", "magenta_terracotta", "magenta_concrete_powder", "magenta_concrete", "magenta_stained_glass", "magenta_stained_glass_pane", "magenta_glazed_terracotta", "magenta_bed", "magenta_carpet", "magenta_shulker_box", "magenta_candle", "magenta_candle_cake", "magenta_banner", "magenta_wall_banner"),
    PINK(DyeColor.PINK, "pink_dye", "pink_wool", "pink_terracotta", "pink_concrete_powder", "pink_concrete", "pink_stained_glass", "pink_stained_glass_pane", "pink_glazed_terracotta", "pink_bed", "pink_carpet", "pink_shulker_box", "pink_candle", "pink_candle_cake", "pink_banner", "pink_wall_banner");

    private static final Map<DyeColor, Dye> byDyeColor = new EnumMap<>(DyeColor.class);
    private static final Map<Material, Dye> byMaterial = new HashMap<>();

    static {
        for (Dye dye : Dye.values()) {
            byDyeColor.put(dye.color, dye);

            for (Material material : dye.materials) {
                if (material != null) {
                    byMaterial.put(material, dye);
                }
            }
        }
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

    private final DyeColor color;
    private final ChatColor chatColor;
    private final Material[] materials;

    private Dye(DyeColor color, String... keys) {
        this.color = color;
        this.chatColor = ChatColor.of(new java.awt.Color(color.getColor().asRGB()));
        this.materials = Arrays.stream(keys).map(s -> Material.matchMaterial(s)).toArray(Material[]::new);
    }

    public Color getColor() {
        return this.color.getColor();
    }

    public DyeColor getDyeColor() {
        return this.color;
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
