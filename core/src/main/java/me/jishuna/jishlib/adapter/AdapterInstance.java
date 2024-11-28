package me.jishuna.jishlib.adapter;

import me.jishuna.jishlib.JishlibPlugin;
import me.jishuna.jishlib.util.MinecraftVersion;
import me.jishuna.jishlib.util.SemanticVersion;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

final class AdapterInstance {
    private static final String path = "me.jishuna.jishlib.nms.%s.NMSAdapter";
    private static final Map<SemanticVersion, String> ADAPTER_MAP = new HashMap<>();

    private static Adapter adapter;

    static {
        ADAPTER_MAP.put(MinecraftVersion.MC1_21_3, "v1_21_R3");
    }

    static Adapter getAdapter() {
        if (adapter == null) {
            adapter = initAdapter();
            JishlibPlugin.instance().getLogger().log(Level.INFO, "Active adapter: {0}", adapter.getClass().getName());
        }
        return adapter;
    }

    static Adapter initAdapter() {
        String adapterVersion = ADAPTER_MAP.get(MinecraftVersion.CURRENT_VERSION);
        if (adapterVersion == null) {
            return new BukkitFallbackAdapter();
        } else {
            try {
                Constructor<?> constructor = Class.forName(path.formatted(adapterVersion)).getDeclaredConstructor();
                constructor.setAccessible(true);
                return (Adapter) constructor.newInstance();
            } catch (ReflectiveOperationException e) {
                return new BukkitFallbackAdapter();
            }
        }
    }
}
