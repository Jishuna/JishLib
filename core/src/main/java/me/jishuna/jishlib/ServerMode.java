package me.jishuna.jishlib;

import org.bukkit.Bukkit;
import me.jishuna.jishlib.paper.PaperHelper;

public enum ServerMode {
    ONLINE, OFFLINE, BUNGEE, VELOCITY;

    private static ServerMode mode;

    public static ServerMode getServerMode() {
        if (mode == null) {
            if (Bukkit.getOnlineMode()) {
                mode = ONLINE;
            } else if (Bukkit.spigot().getConfig().getBoolean("settings.bungeecord", false)) {
                mode = BUNGEE;
            } else if (PaperHelper.isVelocity()) {
                mode = VELOCITY;
            } else {
                mode = OFFLINE;
            }
        }

        return mode;
    }
}
