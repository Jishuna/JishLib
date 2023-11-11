package me.jishuna.jishlib.util;

import org.bukkit.Location;

/**
 * Utility methods for {@link Location}.
 */
public class LocationUtils {

    /**
     * Centers the provided location so that it represents the middle of a block.
     *
     * @param location the location
     * @return a new location centered on the middle of a block
     */
    public static Location centerLocation(final Location location) {
        return centerLocation(location, true);
    }

    /**
     * Centers the provided location so that it represents the middle of a block.
     *
     * @param location the location
     * @param vertical whether to also center the location on the Y axis
     * @return a new location centered on the middle of a block
     */
    public static Location centerLocation(final Location location, boolean vertical) {
        double x = location.getBlockX() + 0.5;
        double y = location.getBlockY() + (vertical ? 0.5 : 0);
        double z = location.getBlockZ() + 0.5;

        return new Location(location.getWorld(), x, y, z);
    }

    private LocationUtils() {
    }
}
