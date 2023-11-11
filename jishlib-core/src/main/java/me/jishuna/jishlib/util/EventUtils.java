package me.jishuna.jishlib.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Utility methods for {@link Event}.
 */
public class EventUtils {
    private EventUtils() {
    }

    /**
     * Gets the attacking entity responsible for the event. If the attacker is a
     * projectile with an entity shooter the shooter will be returned instead.
     *
     * @param event the event
     * @return entity responsible for the attack
     */
    public static Entity getAttackingEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Entity shooter) {
                return shooter;
            }
            return projectile;
        }
        return damager;
    }

}
