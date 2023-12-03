package me.jishuna.jishlib;

import me.jishuna.jishlib.nms.NMSAPI;

public class MinecraftVersion {
    public static final SemanticVersion CURRENT_VERSION = SemanticVersion.fromString(NMSAPI.getServerVersion());

    public static final SemanticVersion MC1_18_1 = new SemanticVersion(1, 18, 1);
    public static final SemanticVersion MC1_18_2 = new SemanticVersion(1, 18, 2);
    public static final SemanticVersion MC1_19_1 = new SemanticVersion(1, 19, 1);
    public static final SemanticVersion MC1_19_2 = new SemanticVersion(1, 19, 2);
    public static final SemanticVersion MC1_19_3 = new SemanticVersion(1, 19, 3);
    public static final SemanticVersion MC1_19_4 = new SemanticVersion(1, 19, 4);
    public static final SemanticVersion MC1_20_1 = new SemanticVersion(1, 20, 1);
    public static final SemanticVersion MC1_20_2 = new SemanticVersion(1, 20, 2);
    public static final SemanticVersion MC1_20_3 = new SemanticVersion(1, 20, 3);

    private MinecraftVersion() {
    }
}
