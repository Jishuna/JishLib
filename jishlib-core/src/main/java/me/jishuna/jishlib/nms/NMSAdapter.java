package me.jishuna.jishlib.nms;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.nms.inventory.CustomAnvilMenu;
import me.jishuna.jishlib.nms.nbt.tag.CompoundTag;

public interface NMSAdapter {
    public CustomAnvilMenu createAnvilMenu(HumanEntity player, String title);

    public byte[] serializeItem(ItemStack item);

    public ItemStack deserializeItem(byte[] bytes);

    public CompoundTag extractNBT(Entity entity);

    public void applyNBT(Entity entity, CompoundTag tag);

    public Entity createEntity(CompoundTag tag, Location location);
}
