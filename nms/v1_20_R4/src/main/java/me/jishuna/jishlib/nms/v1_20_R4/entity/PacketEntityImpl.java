package me.jishuna.jishlib.nms.v1_20_R4.entity;

import com.google.common.collect.MapMaker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.network.syncher.SynchedEntityData.DataValue;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.entity.Player;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.jishuna.jishlib.nms.entity.PacketEntity;

public class PacketEntityImpl extends Entity implements PacketEntity {
    private final Set<ServerPlayer> trackingPlayers = Collections.newSetFromMap(new MapMaker().weakKeys().makeMap());
    private final CraftPacketEntity craftEntity;

    public PacketEntityImpl(org.bukkit.entity.EntityType type, Location location, Level level) {
        super(CraftEntityType.bukkitToMinecraft(type), level);
        this.craftEntity = new CraftPacketEntity(level.getCraftServer(), this);

        setPos(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void startTracking(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        if (!this.trackingPlayers.add(serverPlayer)) {
            return;
        }

        List<Packet<? super ClientGamePacketListener>> packets = new ArrayList<>();

        packets.add(getAddEntityPacket());
        List<DataValue<?>> dataValues = getEntityData().getNonDefaultValues();
        if (dataValues != null) {
            packets.add(new ClientboundSetEntityDataPacket(getId(), dataValues));
        }

        List<Entity> passengers = getPassengers();
        if (!passengers.isEmpty()) {
            for (Entity passenger : passengers) {
                packets.add(passenger.getAddEntityPacket());
                dataValues = passenger.getEntityData().getNonDefaultValues();
                if (dataValues != null) {
                    packets.add(new ClientboundSetEntityDataPacket(passenger.getId(), dataValues));
                }
            }
            packets.add(new ClientboundSetPassengersPacket(this));
        }
        serverPlayer.connection.send(new ClientboundBundlePacket(packets));
    }

    @Override
    public void stopTracking(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        if (!this.trackingPlayers.remove(serverPlayer)) {
            return;
        }

        IntList ids = new IntImmutableList(getSelfAndPassengers().map(Entity::getId).toList());
        serverPlayer.connection.send(new ClientboundRemoveEntitiesPacket(ids));
    }

    @Override
    public Location getLocation() {
        return CraftLocation.toBukkit(position(), level().getWorld(), getBukkitYaw(), getXRot());
    }

    @Override
    public void setLocation(Location location) {
        setPos(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void setBurning(boolean burning) {
        setSharedFlag(0, burning);
        updateSyncedData();
    }

    @Override
    public void setGlowing(boolean glowing) {
        setSharedFlag(6, glowing);
        updateSyncedData();
    }

    @Override
    public void remove() {
        this.remove(RemovalReason.DISCARDED);
    }

    public void broadcast(Packet<ClientGamePacketListener> packet) {
        if (this.trackingPlayers == null) {
            return;
        }

        this.trackingPlayers.forEach(p -> p.connection.send(packet));
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        updatePosition();
    }

    @Override
    public CraftPacketEntity getBukkitEntity() {
        return this.craftEntity;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        // NO-OP
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        // NO-OP
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        // NO-OP
    }

    private void updateSyncedData() {
        List<DataValue<?>> dataValues = getEntityData().packDirty();
        if (dataValues != null) {
            broadcast(new ClientboundSetEntityDataPacket(getId(), dataValues));
        }
    }

    private void updatePosition() {
        Vec3 position = position();

        double x = position.x - this.xOld;
        double y = position.y - this.yOld;
        double z = position.z - this.zOld;

        if (Math.abs(x) >= 8 || Math.abs(y) >= 8 || Math.abs(z) >= 8) {
            broadcast(new ClientboundTeleportEntityPacket(this));
        } else {
            short relX = (short) ((position.x * 32 - this.xOld * 32) * 128);
            short relY = (short) ((position.y * 32 - this.yOld * 32) * 128);
            short relZ = (short) ((position.z * 32 - this.zOld * 32) * 128);

            broadcast(new ClientboundMoveEntityPacket.Pos(getId(), relX, relY, relZ, false));
        }
    }
}
