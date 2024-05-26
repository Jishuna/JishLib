package me.jishuna.jishlib.util;

public final class Capabilities {
    public static final boolean DISPLAY_ENTITIES = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_19_4);
    public static final boolean MULTIPLE_RESOURCE_PACKS = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_20_3);
    public static final boolean ITEM_COMPONENTS = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_20_5);
    public static final boolean NEW_ATTRIBUTES = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_20_5);
    public static final boolean NMS = me.jishuna.jishlib.nms.NMS.initialize();
    public static final boolean PAPER = ReflectionHelper.getClass("com.destroystokyo.paper.PaperConfig") != null ||
            ReflectionHelper.getClass("io.papermc.paper.configuration.Configuration") != null;

    public static void init() {
        // Force the class to load
    }

    private Capabilities() {
    }
}
