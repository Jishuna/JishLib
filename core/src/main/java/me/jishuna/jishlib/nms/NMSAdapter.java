package me.jishuna.jishlib.nms;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public interface NMSAdapter {
    public <T> void spawnParticle(Player player, Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, boolean force, T data);

    public <T> void spawnParticle(Player player, Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, boolean force, T data);

    public void setItemNameComponent(ItemMeta meta, Component component);

    public void addItemLoreComponents(ItemMeta meta, Component... lore);
}
