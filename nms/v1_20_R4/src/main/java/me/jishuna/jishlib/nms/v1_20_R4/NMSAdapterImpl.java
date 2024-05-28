package me.jishuna.jishlib.nms.v1_20_R4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.CraftParticle;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftContainer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.Logger;
import me.jishuna.jishlib.nms.NMSAdapter;
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
    public <T> void spawnParticle(Player player, Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, boolean force, T data) {
        spawnParticle(player, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, force, data);
    }

    @Override
    public <T> void spawnParticle(Player player, Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, boolean force, T data) {
        ClientboundLevelParticlesPacket packetplayoutworldparticles = new ClientboundLevelParticlesPacket(CraftParticle.createParticleParam(particle, data), force, x, y, z, (float) offsetX, (float) offsetY, (float) offsetZ, (float) extra, count);
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

        serverPlayer.connection.send(packetplayoutworldparticles);
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
    public void addItemLoreComponents(ItemMeta meta, Component... lore) {
        try {
            List<net.minecraft.network.chat.Component> nmsLore = (List<net.minecraft.network.chat.Component>) LORE_FIELD.get(meta);
            if (nmsLore == null) {
                nmsLore = new ArrayList<>();
            }

            for (Component component : lore) {
                net.minecraft.network.chat.Component nmsComponent = (net.minecraft.network.chat.Component) Constants.MOJANG_SERIALIZER.serialize(component);
                if (nmsComponent != null) {
                    nmsLore.add(nmsComponent);
                }
            }

            LORE_FIELD.set(meta, nmsLore);

        } catch (Exception e) {
            Logger.error("An unexpected error occured while modifying item lore: {0}", e);
        }
    }
}
