package me.jishuna.jishlib.nms;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import me.jishuna.jishlib.JishLib;

public class NMSAPI {
    private static final String PACKAGE = "me.jishuna.jishlib.nms.";

    private static final Map<String, String> ADAPTER_MAP = new HashMap<>();
    private static NMSAdapter adapter;

    static {
        ADAPTER_MAP.put("1.20.4", "latest");
        ADAPTER_MAP.put("1.19.4", "v1_19_4");
        ADAPTER_MAP.put("1.18.2", "v1_18_2");
    }

    public static void initialize() {
        Preconditions.checkArgument(adapter == null, "NMS already initialized!");
        Preconditions.checkArgument(JishLib.isInitialized(), "JishLib must be initialized first!");

        String version = getAdapterVersion(getServerVersion(), JishLib.getLogger());

        try {
            adapter = (NMSAdapter) Class.forName(PACKAGE + version + ".NMSAdapterImpl").getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            JishLib.getLogger().log(Level.SEVERE, "Failed to load adapter! Please check for updates.");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(JishLib.getPlugin());
        }
    }

    public static NMSAdapter getAdapter() {
        if (adapter == null) {
            throw new IllegalStateException("NMS not initialized!");
        }
        return adapter;
    }

    public static String getServerVersion() {
        String version = Bukkit.getServer().getBukkitVersion();
        if (version.contains("-")) {
            return version.substring(0, version.indexOf('-'));
        }

        return version;
    }

    private static String getAdapterVersion(String serverVersion, Logger logger) {
        String version = ADAPTER_MAP.get(serverVersion);
        if (version == null) {
            logger.log(Level.WARNING, "The server version {0} is not explicitly supported. Attempting to load adapter anyway...", serverVersion);
            version = "latest";
        } else {
            logger.log(Level.INFO, "Known server version detected: {0}. Loading adapter...", serverVersion);
        }
        return version;
    }

    private NMSAPI() {
    }
}
