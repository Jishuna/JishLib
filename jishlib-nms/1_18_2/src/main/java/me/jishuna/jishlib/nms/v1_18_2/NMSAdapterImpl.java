package me.jishuna.jishlib.nms.v1_18_2;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.nms.inventory.CustomAnvilMenu;

public class NMSAdapterImpl extends me.jishuna.jishlib.nms.v1_19_4.NMSAdapterImpl {
    public NMSAdapterImpl() {
        this.nbtUtils = new NBTUtils();
    }

    @SuppressWarnings("deprecation")
    @Override
    public CustomAnvilMenu createAnvilMenu(HumanEntity player, String title) {
        ServerPlayer nmsPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(player.getUniqueId());
        return new CustomAnvil(nmsPlayer, new TextComponent(title));
    }

    @SuppressWarnings("deprecation")
    @Override
    public Entity createEntity(me.jishuna.jishlib.nms.nbt.tag.CompoundTag tag, Location location) {
        ResourceKey<Level> key = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(location.getWorld().getKey().toString()));
        ServerLevel level = MinecraftServer.getServer().getLevel(key);
        if (level == null) {
            return null;
        }

        net.minecraft.world.entity.Entity nms = EntityType.loadEntityRecursive(this.nbtUtils.toNMS(tag, new CompoundTag()), level, en -> {
            en.setPos(location.getX(), location.getY(), location.getZ());
            return en;
        });

        if (nms == null) {
            return null;
        }

        level.addFreshEntityWithPassengers(nms);
        try {
            return (Entity) GET_BUKKIT_ENTITY.invoke(nms);
        } catch (Exception e) {
            return null;
        }
    }
}
