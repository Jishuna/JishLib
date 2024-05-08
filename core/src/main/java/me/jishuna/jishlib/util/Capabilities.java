package me.jishuna.jishlib.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class Capabilities {
    public static final boolean DISPLAY_ENTITIES = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_19_4);
    public static final boolean MULTIPLE_RESOURCE_PACKS = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_20_3);
    public static final boolean ITEM_COMPONENTS = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_20_5);
    public static final boolean NEW_ATTRIBUTES = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_20_5);
    public static final boolean NMS = me.jishuna.jishlib.nms.NMS.isAvailable();
    public static final boolean PAPER = ReflectionHelper.hasClass("com.destroystokyo.paper.PaperConfig") || ReflectionHelper.hasClass("io.papermc.paper.configuration.Configuration");

    public static List<String> getStatus() {
        List<String> status = new ArrayList<>();

        for (Field field : Capabilities.class.getDeclaredFields()) {
            if (field.getType() == boolean.class && Modifier.isStatic(field.getModifiers())) {
                try {
                    status.add(field.getName() + ": " + field.getBoolean(null));
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }

        return status;
    }

    private Capabilities() {
    }
}
