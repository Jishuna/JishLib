package me.jishuna.jishlib.nms;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import me.jishuna.jishlib.nms.entity.CustomEntity;

public interface NMSAdapter {

    public CustomEntity spawnCustomEntity(Location location, EntityType type);

}
