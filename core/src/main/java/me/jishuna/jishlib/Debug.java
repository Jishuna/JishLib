package me.jishuna.jishlib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import me.jishuna.jishlib.event.EventBus;
import me.jishuna.jishlib.util.MinecraftVersion;

public class Debug {

    public static void writeDebugLog(File file) {
        List<String> lines = new ArrayList<>();
        lines.add("%s: %s".formatted("Server Version", MinecraftVersion.CURRENT_VERSION));
        lines.add("%s: %s version %s (Implementing API version %s)".formatted("Server Software", Bukkit.getName(), Bukkit.getVersion(), Bukkit.getBukkitVersion()));
        lines.add("%s: %s".formatted("Online Players", Bukkit.getOnlinePlayers().size()));
        lines.add("");
        lines.add("%s: %s".formatted("NMS Adapter", Capabilities.NMS));
        lines.add("");
        lines.add("Plugins:");

        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            lines.add(" - %s v%s".formatted(plugin.getName(), plugin.getDescription().getVersion()));
        }

        lines.add("");
        lines.add("Events:");

        Map<String, List<String>> eventData = new HashMap<>();
        for (Cleanable cleanable : me.jishuna.jishlib.Plugin.getInstance().cleanables) {
            if (cleanable instanceof EventBus bus) {
                bus.writeDebugData(eventData);
            }
        }

        eventData.forEach((k, v) -> {
            lines.add("  " + k);
            v.forEach(s -> lines.add("   - " + s));
        });

        try {
            Logger.info("Writing debug log to {0}", file.getPath());
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
