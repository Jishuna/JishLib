package me.jishuna.jishlib.pdc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class ItemStackType implements PersistentDataType<byte[], ItemStack> {

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<ItemStack> getComplexType() {
        return ItemStack.class;
    }

    @Override
    public byte[] toPrimitive(ItemStack complex, PersistentDataAdapterContext context) {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(byteStream)) {
            bukkitStream.writeObject(complex);
            return byteStream.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    @Override
    public ItemStack fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
        if (primitive.length == 0) {
            return new ItemStack(Material.AIR);
        }

        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(primitive);
                BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(byteStream)) {
            return (ItemStack) bukkitStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ItemStack(Material.AIR);
        }
    }
}
