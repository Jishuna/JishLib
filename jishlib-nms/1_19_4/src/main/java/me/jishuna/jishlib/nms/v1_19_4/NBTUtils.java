package me.jishuna.jishlib.nms.v1_19_4;

import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import me.jishuna.jishlib.nms.nbt.NBTTag;

public class NBTUtils extends me.jishuna.jishlib.nms.latest.NBTUtils {
    @Override
    protected NBTTag<?> fromNMS(Tag baseTag) {
        if (baseTag instanceof CompoundTag tag) {
            return fromNMS(tag, new me.jishuna.jishlib.nms.nbt.tag.CompoundTag());
        }
        if (baseTag instanceof ListTag tag) {
            return fromNMS(tag, new me.jishuna.jishlib.nms.nbt.tag.ListTag());
        }
        if (baseTag instanceof ByteTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.ByteTag(tag.getAsByte());
        }
        if (baseTag instanceof ShortTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.ShortTag(tag.getAsShort());
        }
        if (baseTag instanceof IntTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.IntTag(tag.getAsInt());
        }
        if (baseTag instanceof LongTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.LongTag(tag.getAsLong());
        }
        if (baseTag instanceof FloatTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.FloatTag(tag.getAsFloat());
        }
        if (baseTag instanceof DoubleTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.DoubleTag(tag.getAsDouble());
        }
        if (baseTag instanceof StringTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.StringTag(tag.getAsString());
        }
        if (baseTag instanceof ByteArrayTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.ByteArrayTag(tag.getAsByteArray());
        }
        if (baseTag instanceof IntArrayTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.IntArrayTag(tag.getAsIntArray());
        }
        if (baseTag instanceof LongArrayTag tag) {
            return new me.jishuna.jishlib.nms.nbt.tag.LongArrayTag(tag.getAsLongArray());
        }

        if (baseTag != null) {
            System.out.println("Unhandled type: " + baseTag.getClass().getName());
        }
        return null;
    }
}
