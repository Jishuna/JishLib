package me.jishuna.jishlib.adapter;

import me.jishuna.jishlib.util.MinecraftVersion;
import me.jishuna.jishlib.util.SemanticVersion;

import java.util.HashMap;
import java.util.Map;

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
            System.out.println(adapter.getClass().getName());
        }
        return adapter;
    }

    static Adapter initAdapter() {
        String adapterVersion = ADAPTER_MAP.get(MinecraftVersion.CURRENT_VERSION);
        if (adapterVersion == null) {
            return new BukkitFallbackAdapter();
        } else {
            try {
                return (Adapter) Class.forName(path.formatted(adapterVersion)).getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                return new BukkitFallbackAdapter();
            }
        }
    }
}
