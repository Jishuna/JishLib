package me.jishuna.jishlib;

import me.jishuna.jishlib.util.ReflectionHelper;

public final class Capabilities {
    public static final boolean NMS = me.jishuna.jishlib.nms.NMS.initialize();
    public static final boolean PAPER = ReflectionHelper.getClass("com.destroystokyo.paper.PaperConfig") != null ||
            ReflectionHelper.getClass("io.papermc.paper.configuration.Configuration") != null;

    public static void init() {
        // Force the class to load
    }

    private Capabilities() {
    }
}
