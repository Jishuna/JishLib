package me.jishuna.jishlib.nms.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.nms.NMS;

public interface PacketEntity {

    public static PacketEntity create(EntityType type, Location location) {
        return NMS.get().createPacketEntity(type, location);
    }

    public void startTracking(Player player);

    public void stopTracking(Player player);

    public Location getLocation();

    public void setLocation(Location location);

    public void setBurning(boolean burning);

    public void setGlowing(boolean glowing);

    public void remove();
}
