package me.jishuna.jishlib.nms;

import java.util.HashMap;
import java.util.Map;
import me.jishuna.jishlib.util.MinecraftVersion;
import me.jishuna.jishlib.util.ReflectionHelper;
import me.jishuna.jishlib.util.SemanticVersion;

public class NMS {
    private static final String PACKAGE = "me.jishuna.jishlib.nms.";
    private static final Map<SemanticVersion, String> ADAPTER_MAP = new HashMap<>();

    private static NMSAdapter ADAPTER;

    static {
        ADAPTER_MAP.put(MinecraftVersion.MC1_20_6, "v1_20_3");
        ADAPTER_MAP.put(MinecraftVersion.MC1_20_5, "v1_20_3");
    }

    public static boolean isAvailable() {
        String adapterVersion = ADAPTER_MAP.get(MinecraftVersion.CURRENT_VERSION);
        if (adapterVersion == null) {
            return false;
        }

        return ReflectionHelper.hasClass(PACKAGE + adapterVersion + ".NMSAdapterImpl");
    }

    public static boolean initialize() {
        if (ADAPTER != null) {
            throw new IllegalStateException("NMS already initialized");
        }

        String adapterVersion = ADAPTER_MAP.get(MinecraftVersion.CURRENT_VERSION);
        if (adapterVersion == null) {
            return false;
        }

        try {
            ADAPTER = (NMSAdapter) Class.forName(PACKAGE + adapterVersion + ".NMSAdapterImpl").getDeclaredConstructor().newInstance();
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public static NMSAdapter get() {
        if (ADAPTER == null) {
            throw new IllegalStateException("NMS not initialized!");
        }
        return ADAPTER;
    }
}
