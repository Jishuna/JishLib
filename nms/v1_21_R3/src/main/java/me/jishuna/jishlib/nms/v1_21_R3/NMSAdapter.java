package me.jishuna.jishlib.nms.v1_21_R3;

import com.mojang.serialization.Dynamic;
import me.jishuna.jishlib.adapter.Adapter;
import me.jishuna.jishlib.reflection.FieldAccess;
import me.jishuna.jishlib.reflection.ReflectionException;
import me.jishuna.jishlib.reflection.ReflectionHelper;
import net.kyori.adventure.platform.bukkit.MinecraftComponentSerializer;
import net.kyori.adventure.text.Component;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftContainer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NMSAdapter implements Adapter {
    private static final NbtOps NBT_OPS = NbtOps.INSTANCE;
    private static final RegistryAccess.Frozen REGISTRY_ACCESS = MinecraftServer.getServer().registryAccess();

    private static FieldAccess<net.minecraft.network.chat.Component> NAME_FIELD;
    private static FieldAccess<List> LORE_FIELD;

    static {
        try {
            Class<?> craftMetaItemClass = ReflectionHelper.getCraftClass(".inventory.CraftMetaItem");
            NAME_FIELD = ReflectionHelper.getField(craftMetaItemClass, net.minecraft.network.chat.Component.class, "displayName");
            LORE_FIELD = ReflectionHelper.getField(craftMetaItemClass, List.class, "lore");
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
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

        net.minecraft.network.chat.Component nmsComponent = (net.minecraft.network.chat.Component) MinecraftComponentSerializer.get().serialize(component);
        nmsPlayer.connection.send(new ClientboundOpenScreenPacket(menu.containerId, type, nmsComponent));
        nmsPlayer.containerMenu = menu;
        nmsPlayer.initMenu(menu);
    }

    @Override
    public Collection<Component> getLore(ItemMeta meta) {
        List<Component> lore = new ArrayList<>();
        try {
            List<net.minecraft.network.chat.Component> nmsLore = (List<net.minecraft.network.chat.Component>) LORE_FIELD.readSafe(meta, null);
            if (nmsLore != null) {
                for (net.minecraft.network.chat.Component component : nmsLore) {
                    lore.add(MinecraftComponentSerializer.get().deserializeOr(component, Component.empty()));
                }

            }
        } catch (Exception ignored) {
        }
        return lore;
    }

    @Override
    public void setLore(ItemMeta meta, Collection<Component> lore) {
        try {
            List<net.minecraft.network.chat.Component> nmsLore = new ArrayList<>();
            for (Component component : lore) {
                Object nmsComponent = MinecraftComponentSerializer.get().serialize(component);
                if (nmsComponent instanceof net.minecraft.network.chat.Component nms) {
                    nmsLore.add(nms);
                }
            }

            LORE_FIELD.writeSafe(meta, nmsLore);

        } catch (Exception ignored) {
        }
    }

    @Override
    public Component getName(ItemMeta meta) {
        try {
            net.minecraft.network.chat.Component nmsName = NAME_FIELD.readSafe(meta, null);
            return MinecraftComponentSerializer.get().deserializeOr(nmsName, null);
        } catch (Exception ignored) {
        }

        return null;
    }

    @Override
    public void setName(ItemMeta meta, Component name) {
        try {
            net.minecraft.network.chat.Component nmsName = (net.minecraft.network.chat.Component) MinecraftComponentSerializer.get().serialize(name);
            NAME_FIELD.writeSafe(meta, nmsName);
        } catch (Exception ignored) {
        }
    }

    @Override
    public byte[] serializeItem(ItemStack item) {
        CompoundTag tag = (CompoundTag) CraftItemStack.asNMSCopy(item).save(REGISTRY_ACCESS);
        return NBTHelper.toBytes(tag);
    }

    @Override
    public ItemStack deserializeItem(byte[] bytes) {
        CompoundTag compound = NBTHelper.fromBytes(bytes);
        int dataVersion = compound.getInt("DataVersion");
        Tag tag = DataFixers.getDataFixer().update(References.ITEM_STACK, new Dynamic<>(NBT_OPS, compound), dataVersion, NBTHelper.getDataVersion()).getValue();

        return CraftItemStack.asCraftMirror(net.minecraft.world.item.ItemStack.parse(REGISTRY_ACCESS, tag).orElseThrow());
    }
}
