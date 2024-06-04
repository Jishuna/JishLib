package me.jishuna.jishlib.paper;

import org.bukkit.Bukkit;

public class PaperHelper {
    private static final boolean HAS_PAPER = isPaper();

    public static boolean isVelocity() {
        if (!HAS_PAPER) {
            return false;
        }

        return Bukkit.spigot().getPaperConfig().getBoolean("proxies.velocity.enabled", false);
    }

    private static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            Class.forName("io.papermc.paper.configuration.Configuration");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
