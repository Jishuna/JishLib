package me.jishuna.jishlib.pdc;

import me.jishuna.jishlib.util.Key;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public final class PDCHelper {

    public static void set(PersistentDataHolder holder, Key key, String value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.STRING, value);
    }

    public static void set(PersistentDataHolder holder, Key key, byte value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.BYTE, value);
    }

    public static void set(PersistentDataHolder holder, Key key, short value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.SHORT, value);
    }

    public static void set(PersistentDataHolder holder, Key key, int value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.INTEGER, value);
    }

    public static void set(PersistentDataHolder holder, Key key, long value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.LONG, value);
    }

    public static void set(PersistentDataHolder holder, Key key, float value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.FLOAT, value);
    }

    public static void set(PersistentDataHolder holder, Key key, double value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.DOUBLE, value);
    }

    public static void set(PersistentDataHolder holder, Key key, boolean value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PersistentDataType.BOOLEAN, value);
    }

    public static void set(PersistentDataHolder holder, Key key, Key value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PDCTypes.KEY, value);
    }

    public static void set(PersistentDataHolder holder, Key key, UUID value) {
        holder.getPersistentDataContainer().set(key.toBukkit(), PDCTypes.UUID, value);
    }

    public static String getString(PersistentDataHolder holder, Key key) {
        return getString(holder, key, null);
    }

    public static String getString(PersistentDataHolder holder, Key key, String def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.STRING, def);
    }

    public static byte getByte(PersistentDataHolder holder, Key key) {
        return getByte(holder, key, (byte) -1);
    }

    public static byte getByte(PersistentDataHolder holder, Key key, byte def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.BYTE, def);
    }

    public static short getShort(PersistentDataHolder holder, Key key) {
        return getShort(holder, key, (short) -1);
    }

    public static short getShort(PersistentDataHolder holder, Key key, short def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.SHORT, def);
    }

    public static int getInt(PersistentDataHolder holder, Key key) {
        return getInt(holder, key, -1);
    }

    public static int getInt(PersistentDataHolder holder, Key key, int def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.INTEGER, def);
    }

    public static long getLong(PersistentDataHolder holder, Key key) {
        return getLong(holder, key, -1L);
    }

    public static long getLong(PersistentDataHolder holder, Key key, long def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.LONG, def);
    }

    public static float getFloat(PersistentDataHolder holder, Key key) {
        return getFloat(holder, key, -1F);
    }

    public static float getFloat(PersistentDataHolder holder, Key key, float def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.FLOAT, def);
    }

    public static double getDouble(PersistentDataHolder holder, Key key) {
        return getDouble(holder, key, -1D);
    }

    public static double getDouble(PersistentDataHolder holder, Key key, double def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.DOUBLE, def);
    }

    public static boolean getBoolean(PersistentDataHolder holder, Key key) {
        return getBoolean(holder, key, false);
    }

    public static boolean getBoolean(PersistentDataHolder holder, Key key, boolean def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PersistentDataType.BOOLEAN, def);
    }

    public static Key getKey(PersistentDataHolder holder, Key key) {
        return getKey(holder, key, null);
    }

    public static Key getKey(PersistentDataHolder holder, Key key, Key def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PDCTypes.KEY, def);
    }

    public static UUID getUUID(PersistentDataHolder holder, Key key) {
        return getUUID(holder, key, null);
    }

    public static UUID getUUID(PersistentDataHolder holder, Key key, UUID def) {
        return holder.getPersistentDataContainer().getOrDefault(key.toBukkit(), PDCTypes.UUID, def);
    }

    private PDCHelper() {
    }
}
