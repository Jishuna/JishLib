package me.jishuna.jishlib.pdc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import me.jishuna.jishlib.item.ItemUtils;

public class ItemStackPersistentDataType implements PersistentDataType<byte[], ItemStack> {

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
        return ItemUtils.toBytes(complex);
    }

    @Override
    public ItemStack fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
        return ItemUtils.fromBytes(primitive);
    }
}
