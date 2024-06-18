package me.jishuna.jishlib.nms.v1_20_R4.entity;

import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;

public class CraftPacketEntity extends CraftEntity {

    public CraftPacketEntity(CraftServer server, Entity entity) {
        super(server, entity);
    }
}
