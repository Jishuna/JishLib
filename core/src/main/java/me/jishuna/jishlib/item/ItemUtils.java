package me.jishuna.jishlib.item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public final class ItemUtils {

    public static byte[] toBytes(ItemStack item) {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(byteStream)) {
            bukkitStream.writeObject(item);
            return byteStream.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static ItemStack fromBytes(byte[] bytes) {
        if (bytes.length == 0) {
            return new ItemStack(Material.AIR);
        }

        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(byteStream)) {
            Object obj = bukkitStream.readObject();
            if (obj instanceof ItemStack item) {
                return item;
            }
        } catch (IOException | ClassNotFoundException e) {
        }
        return new ItemStack(Material.AIR);
    }

    private ItemUtils() {
    }
}
