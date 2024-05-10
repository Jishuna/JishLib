package me.jishuna.jishlib.nms.v1_20_R4;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.entity.EntityType;
import me.jishuna.jishlib.nms.NMSAdapter;
import me.jishuna.jishlib.nms.entity.CustomEntity;

public class NMSAdapterImpl implements NMSAdapter {

    @Override
    public CustomEntity spawnCustomEntity(Location location, EntityType type) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        net.minecraft.world.entity.EntityType<?> nmsType = CraftEntityType.bukkitToMinecraft(type);

        InternalCustomEntity entity = new InternalCustomEntity(level, nmsType);
        entity.setPos(location.getX(), location.getY(), location.getZ());
        level.addFreshEntityWithPassengers(entity);

        return entity.getBukkitEntity();
    }
}
