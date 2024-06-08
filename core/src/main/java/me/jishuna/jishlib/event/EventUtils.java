package me.jishuna.jishlib.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EventUtils {
    public static Entity getSourceEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Entity shooter) {
                return shooter;
            }
            return projectile;
        }
        return damager;
    }

    private EventUtils() {
    }

}
