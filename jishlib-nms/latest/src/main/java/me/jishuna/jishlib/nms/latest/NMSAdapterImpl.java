package me.jishuna.jishlib.nms.latest;

import com.mojang.serialization.Dynamic;
import java.lang.reflect.Method;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.nms.NMSAdapter;
import me.jishuna.jishlib.nms.NMSException;
import me.jishuna.jishlib.nms.ReflectionUtils;
import me.jishuna.jishlib.nms.inventory.CustomAnvilMenu;

public class NMSAdapterImpl implements NMSAdapter {
    protected NBTUtils nbtUtils;
    protected static final Method ITEM_TO_NMS;
    protected static final Method ITEM_FROM_NMS;
    protected static final Method ENTITY_TO_NMS;
    protected static final Method GET_BUKKIT_ENTITY;

    static {
        try {
            Class<?> craftItemStack = ReflectionUtils.getCraftBukkitClass("inventory.CraftItemStack");
            Class<?> craftEntity = ReflectionUtils.getCraftBukkitClass("entity.CraftEntity");

            ITEM_TO_NMS = craftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);
            ITEM_FROM_NMS = craftItemStack.getDeclaredMethod("asBukkitCopy", net.minecraft.world.item.ItemStack.class);
            ENTITY_TO_NMS = craftEntity.getDeclaredMethod("getHandle");
            GET_BUKKIT_ENTITY = net.minecraft.world.entity.Entity.class.getDeclaredMethod("getBukkitEntity");
        } catch (Exception e) {
            throw new NMSException("Failed to set up reflection", e);
        }
    }

    public NMSAdapterImpl() {
        this.nbtUtils = new NBTUtils();
    }

    @Override
    public CustomAnvilMenu createAnvilMenu(HumanEntity player, String title) {
        ServerPlayer nmsPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(player.getUniqueId());
        return new CustomAnvil(nmsPlayer, Component.literal(title));
    }

    @Override
    public byte[] serializeItem(ItemStack item) {
        try {
            net.minecraft.world.item.ItemStack nms = (net.minecraft.world.item.ItemStack) ITEM_TO_NMS.invoke(null, item);
            return this.nbtUtils.toBytes(nms.save(new CompoundTag()));
        } catch (Exception e) {
            throw new NMSException("Failed to serialize item", e);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ItemStack deserializeItem(byte[] bytes) {
        try {
            CompoundTag compound = this.nbtUtils.fromBytes(bytes);
            int dataVersion = compound.getInt("jl.dataversion");
            Dynamic<CompoundTag> converted = DataFixers.getDataFixer().update(References.ITEM_STACK, new Dynamic(NbtOps.INSTANCE, compound), dataVersion, this.nbtUtils.getDataVersion());
            compound = converted.getValue();

            return (ItemStack) ITEM_FROM_NMS.invoke(null, net.minecraft.world.item.ItemStack.of(compound));
        } catch (Exception e) {
            throw new NMSException("Failed to deserialize item", e);
        }
    }

    @Override
    public me.jishuna.jishlib.nms.nbt.tag.CompoundTag extractNBT(Entity entity) {
        try {
            net.minecraft.world.entity.Entity nms = (net.minecraft.world.entity.Entity) ENTITY_TO_NMS.invoke(entity);
            CompoundTag tag = new CompoundTag();
            nms.saveAsPassenger(tag);
            this.nbtUtils.removeRecursive(tag, "UUID");

            return this.nbtUtils.fromNMS(tag, new me.jishuna.jishlib.nms.nbt.tag.CompoundTag());
        } catch (Exception e) {
            throw new NMSException("Failed to extract entity NBT", e);
        }
    }

    @Override
    public void applyNBT(Entity entity, me.jishuna.jishlib.nms.nbt.tag.CompoundTag tag) {
        try {
            net.minecraft.world.entity.Entity nms = (net.minecraft.world.entity.Entity) ENTITY_TO_NMS.invoke(entity);
            nms.load(this.nbtUtils.toNMS(tag, new CompoundTag()));
        } catch (Exception e) {
            throw new NMSException("Failed to insert entity NBT", e);
        }
    }

    @Override
    public Entity createEntity(me.jishuna.jishlib.nms.nbt.tag.CompoundTag tag, Location location) {
        ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(location.getWorld().getKey().toString()));
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
