package me.jishuna.jishlib.paper;

import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PaperHelper {
    private static final boolean HAS_PAPER = isPaper();

    public static CompletableFuture<Boolean> teleportAsync(Player player, Location location) {
        if (HAS_PAPER) {
            return player.teleportAsync(location);
        }

        return CompletableFuture.completedFuture(player.teleport(location));
    }

    public static CompletableFuture<Chunk> getChunkAsync(World world, Location location) {
        if (HAS_PAPER) {
            return world.getChunkAtAsync(location);
        }

        return CompletableFuture.completedFuture(world.getChunkAt(location));
    }

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
