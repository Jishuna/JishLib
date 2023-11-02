package me.jishuna.jishlib.util;

import org.bukkit.Location;

public class LocationUtils {
    private LocationUtils() {
    }

    public static Location centerLocation(Location location) {
        return centerLocation(location, true);
    }

    public static Location centerLocation(Location location, boolean vertical) {
        double x = location.getBlockX() + 0.5;
        double y = location.getBlockY() + (vertical ? 0.5 : 0);
        double z = location.getBlockZ() + 0.5;

        return new Location(location.getWorld(), x, y, z);
    }
}
