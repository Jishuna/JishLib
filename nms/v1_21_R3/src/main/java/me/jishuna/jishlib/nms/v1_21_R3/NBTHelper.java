package me.jishuna.jishlib.nms.v1_21_R3;

import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NBTHelper {

    public static byte[] toBytes(CompoundTag tag) {
        tag.putInt("DataVersion", getDataVersion());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            net.minecraft.nbt.NbtIo.writeCompressed(tag, outputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return outputStream.toByteArray();
    }

    public static CompoundTag fromBytes(byte[] data) {
        CompoundTag compound;
        try {
            compound = NbtIo.readCompressed(new ByteArrayInputStream(data), NbtAccounter.unlimitedHeap());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return compound;
    }

    public static int getDataVersion() {
        return SharedConstants.getCurrentVersion().getDataVersion().getVersion();
    }
}
