package me.jishuna.jishlib.nms;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.nms.entity.PacketEntity;

public interface NMSAdapter {
    public int getReloadCount();

    public void openInventory(HumanEntity player, Inventory inventory, Component component);

    public Component getItemNameComponent(ItemMeta meta);

    public void setItemNameComponent(ItemMeta meta, Component component);

    public List<Component> getItemLoreComponents(ItemMeta meta);

    public void addItemLoreComponents(ItemMeta meta, Component... lore);

    public PacketEntity createPacketEntity(EntityType type, Location location);

    public MapDataHolder extractNBT(Entity entity);

    public void applyNBT(Entity entity, MapDataHolder data);

    public DataHolder<?> extractNBT(ItemStack item);

    public ItemStack createItemStack(MapDataHolder data);
}
