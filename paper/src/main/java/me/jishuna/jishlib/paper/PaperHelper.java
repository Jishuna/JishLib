package me.jishuna.jishlib.paper;

import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.Capabilities;

public class PaperHelper {
    private static final boolean HAS_PAPER = Capabilities.PAPER;

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
}
