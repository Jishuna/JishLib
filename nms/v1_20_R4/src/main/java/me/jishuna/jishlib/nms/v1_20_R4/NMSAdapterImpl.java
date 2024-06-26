package me.jishuna.jishlib.nms.v1_20_R4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftContainer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.Logger;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.nms.NMSAdapter;
import me.jishuna.jishlib.nms.entity.PacketEntity;
import me.jishuna.jishlib.nms.v1_20_R4.entity.PacketEntityImpl;
import me.jishuna.jishlib.util.ReflectionHelper;

public class NMSAdapterImpl implements NMSAdapter {
    private static final Field DISPLAY_NAME_FIELD;
    private static final Field LORE_FIELD;

    static {
        Class<?> craftMetaItemClass = ReflectionHelper.getCraftClass(".inventory.CraftMetaItem");
        DISPLAY_NAME_FIELD = ReflectionHelper.getField(craftMetaItemClass, "displayName");
        LORE_FIELD = ReflectionHelper.getField(craftMetaItemClass, "lore");
    }

    @Override
    public void openInventory(HumanEntity player, Inventory inventory, Component component) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        MenuType<?> type = CraftContainer.getNotchInventoryType(inventory);

        AbstractContainerMenu menu = new CraftContainer(inventory, nmsPlayer, nmsPlayer.nextContainerCounter());
        menu = CraftEventFactory.callInventoryOpenEvent(nmsPlayer, menu);

        if (type == null || menu == null) {
            return;
        }

        net.minecraft.network.chat.Component nmsComponent = (net.minecraft.network.chat.Component) Constants.MOJANG_SERIALIZER.serialize(component);
        nmsPlayer.connection.send(new ClientboundOpenScreenPacket(menu.containerId, type, nmsComponent));
        nmsPlayer.containerMenu = menu;
        nmsPlayer.initMenu(menu);
    }

    @Override
    public Component getItemNameComponent(ItemMeta meta) {
        try {
            Object nmsComponent = DISPLAY_NAME_FIELD.get(meta);
            return Constants.MOJANG_SERIALIZER.deserializeOr(nmsComponent, Component.empty());
        } catch (Exception e) {
            Logger.error("An unexpected error occured while reading item name: {0}", e);
        }
        return Component.empty();
    }

    @Override
    public void setItemNameComponent(ItemMeta meta, Component component) {
        Object nmsComponent = Constants.MOJANG_SERIALIZER.serialize(component);
        try {
            DISPLAY_NAME_FIELD.set(meta, nmsComponent);
        } catch (Exception e) {
            Logger.error("An unexpected error occured while modifying item name: {0}", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Component> getItemLoreComponents(ItemMeta meta) {
        try {
            List<net.minecraft.network.chat.Component> nmsLore = (List<net.minecraft.network.chat.Component>) LORE_FIELD.get(meta);
            if (nmsLore == null) {
                return Collections.emptyList();
            }

            List<Component> lore = new ArrayList<>();
            for (net.minecraft.network.chat.Component component : nmsLore) {
                lore.add(Constants.MOJANG_SERIALIZER.deserializeOr(component, Component.empty()));
            }

            return lore;
        } catch (Exception e) {
            Logger.error("An unexpected error occured while reading item lore: {0}", e);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addItemLoreComponents(ItemMeta meta, Component... lore) {
        try {
            List<net.minecraft.network.chat.Component> nmsLore = (List<net.minecraft.network.chat.Component>) LORE_FIELD.get(meta);
            if (nmsLore == null) {
                nmsLore = new ArrayList<>();
            }

            for (Component component : lore) {
                net.minecraft.network.chat.Component nmsComponent = (net.minecraft.network.chat.Component) Constants.MOJANG_SERIALIZER.serialize(component);
                nmsLore.add(nmsComponent);
            }

            LORE_FIELD.set(meta, nmsLore);

        } catch (Exception e) {
            Logger.error("An unexpected error occured while modifying item lore: {0}", e);
        }
    }

    @Override
    public PacketEntity createPacketEntity(EntityType type, Location location) {
        Level level = ((CraftWorld) location.getWorld()).getHandle();
        return new PacketEntityImpl(type, location, level);
    }

    @Override
    public MapDataHolder extractNBT(Entity entity) {
        CompoundTag tag = new CompoundTag();
        ((CraftEntity) entity).getHandle().saveAsPassenger(tag);

        return NBTHelper.fromCompound(tag);
    }

    @Override
    public void applyNBT(Entity entity, MapDataHolder data) {
        CompoundTag tag = NBTHelper.toCompound(data);
        ((CraftEntity) entity).getHandle().load(tag);
    }

    @Override
    public DataHolder<?> extractNBT(ItemStack item) {
        Tag tag = CraftItemStack.asNMSCopy(item).save(MinecraftServer.getServer().registryAccess());
        return NBTHelper.fromTag(tag);
    }

    @Override
    public ItemStack createItemStack(MapDataHolder data) {
        CompoundTag tag = NBTHelper.toCompound(data);
        net.minecraft.world.item.ItemStack nmsItem = net.minecraft.world.item.ItemStack.parseOptional(MinecraftServer.getServer().registryAccess(), tag);
        return CraftItemStack.asCraftMirror(nmsItem);
    }
}
