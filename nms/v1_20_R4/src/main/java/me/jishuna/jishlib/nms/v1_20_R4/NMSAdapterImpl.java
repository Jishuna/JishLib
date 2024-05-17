package me.jishuna.jishlib.nms.v1_20_R4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.CraftParticle;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.Logger;
import me.jishuna.jishlib.nms.NMSAdapter;
import me.jishuna.jishlib.util.ReflectionHelper;

public class NMSAdapterImpl implements NMSAdapter {
    public static final String CRAFTBUKKIT_PACKAGE = Bukkit.getServer().getClass().getPackageName();

    private static final Field DISPLAY_NAME_FIELD;
    private static final Field LORE_FIELD;

    static {
        Class<?> craftMetaItemClass = ReflectionHelper.getClass(CRAFTBUKKIT_PACKAGE + ".inventory.CraftMetaItem");
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
    public void setItemNameComponent(ItemMeta meta, Component component) {
        net.minecraft.network.chat.Component nmsComponent = CraftChatMessage.fromJSONOrNull(Constants.GSON_SERIALIZER.serialize(component));
        if (nmsComponent != null) {
            try {
                DISPLAY_NAME_FIELD.set(meta, nmsComponent);
            } catch (Exception e) {
                Logger.error("An unexpected error occured while modifying item name: {0}", e);
            }
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
                net.minecraft.network.chat.Component nmsComponent = CraftChatMessage.fromJSONOrNull(Constants.GSON_SERIALIZER.serialize(component));
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
