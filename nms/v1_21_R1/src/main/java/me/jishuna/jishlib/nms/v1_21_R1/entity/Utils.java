package me.jishuna.jishlib.nms.v1_21_R1.entity;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;

public class Utils {
    public static ClientboundAddEntityPacket getAddEntityPacket(Entity entity) {
        return new ClientboundAddEntityPacket(entity.getId(), entity.getUUID(), entity.getX(), entity.getY(), entity.getZ(), entity.getXRot(), entity.getYRot(), entity.getType(), 0, entity.getDeltaMovement(), entity.getYHeadRot());
    }
}
