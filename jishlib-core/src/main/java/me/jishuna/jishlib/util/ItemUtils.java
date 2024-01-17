package me.jishuna.jishlib.util;

import java.util.EnumMap;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

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

    private ItemUtils() {
    }
}
