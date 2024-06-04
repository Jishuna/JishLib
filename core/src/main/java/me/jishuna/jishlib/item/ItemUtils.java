package me.jishuna.jishlib.item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public final class ItemUtils {

    private static final EnumMap<EquipmentSlot, Consumer<LivingEntity>> SLOT_ACTIONS = new EnumMap<>(EquipmentSlot.class);

    static {
        SLOT_ACTIONS.put(EquipmentSlot.HAND, e -> e.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND));
        SLOT_ACTIONS.put(EquipmentSlot.OFF_HAND, e -> e.playEffect(EntityEffect.BREAK_EQUIPMENT_OFF_HAND));
        SLOT_ACTIONS.put(EquipmentSlot.HEAD, e -> e.playEffect(EntityEffect.BREAK_EQUIPMENT_HELMET));
        SLOT_ACTIONS.put(EquipmentSlot.CHEST, e -> e.playEffect(EntityEffect.BREAK_EQUIPMENT_CHESTPLATE));
        SLOT_ACTIONS.put(EquipmentSlot.LEGS, e -> e.playEffect(EntityEffect.BREAK_EQUIPMENT_LEGGINGS));
        SLOT_ACTIONS.put(EquipmentSlot.FEET, e -> e.playEffect(EntityEffect.BREAK_EQUIPMENT_BOOTS));
    }

    public static boolean reduceDurability(LivingEntity entity, ItemStack item, EquipmentSlot slot) {
        return reduceDurability(entity, item, 1, slot, true);
    }

    public static boolean reduceDurability(LivingEntity entity, ItemStack item, int amount, EquipmentSlot slot, boolean fireEvent) {
        if (entity instanceof Player player && player.getGameMode() == GameMode.CREATIVE) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable damagable)) {
            return false;
        }

        if (fireEvent && entity instanceof Player player) {
            PlayerItemDamageEvent event = new PlayerItemDamageEvent(player, item, amount);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return false;
            }

            amount = event.getDamage();
        }

        damagable.setDamage(damagable.getDamage() + amount);
        if (damagable.getDamage() > item.getType().getMaxDurability()) {
            SLOT_ACTIONS.get(slot).accept(entity);

            entity.getEquipment().setItem(slot, null);
            return true;
        }

        item.setItemMeta(damagable);
        return false;
    }

    public static byte[] toBytes(ItemStack item) {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(byteStream)) {
            bukkitStream.writeObject(item);
            return byteStream.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static ItemStack fromBytes(byte[] bytes) {
        if (bytes.length == 0) {
            return new ItemStack(Material.AIR);
        }

        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(byteStream)) {
            Object obj = bukkitStream.readObject();
            if (obj instanceof ItemStack item) {
                return item;
            }
        } catch (IOException | ClassNotFoundException e) {
        }
        return new ItemStack(Material.AIR);
    }

    private ItemUtils() {
    }
}
