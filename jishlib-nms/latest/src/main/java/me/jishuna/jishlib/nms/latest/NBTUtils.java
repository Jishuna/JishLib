package me.jishuna.jishlib.nms.latest;

import java.io.IOException;
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
import org.bukkit.Bukkit;
import me.jishuna.jishlib.nms.NMSException;
import me.jishuna.jishlib.nms.nbt.NBTTag;

public class NBTUtils {
    @SuppressWarnings("deprecation")
    public int getDataVersion() {
        return Bukkit.getUnsafe().getDataVersion();
    }

    public byte[] toBytes(CompoundTag compound) {
        compound.putInt("jl.dataversion", getDataVersion());
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        try {
            net.minecraft.nbt.NbtIo.writeCompressed(compound, outputStream);
        } catch (IOException ex) {
            throw new NMSException("Failed to save NBT as bytes", ex);
        }
        return outputStream.toByteArray();
    }

    public CompoundTag fromBytes(byte[] data) {
        CompoundTag compound;
        try {
            compound = net.minecraft.nbt.NbtIo.readCompressed(new java.io.ByteArrayInputStream(data));
        } catch (IOException ex) {
            throw new NMSException("Failed to read NBT from bytes", ex);
        }
        return compound;
    }

    public CompoundTag removeRecursive(CompoundTag nms, String... keys) {
        for (String toRemove : keys) {
            nms.remove(toRemove);
        }

        for (String key : nms.getAllKeys()) {
            Tag tag = nms.get(key);
            if (tag instanceof ListTag listTag) {
                removeRecursive(listTag, keys);
            } else if (tag instanceof CompoundTag compound) {
                removeRecursive(compound, keys);
            }
        }

        return nms;
    }

    protected void removeRecursive(ListTag nms, String... keys) {
        nms.forEach(tag -> {
            if (tag instanceof ListTag listTag) {
                removeRecursive(listTag, keys);
            }
        });
    }

    public me.jishuna.jishlib.nms.nbt.tag.CompoundTag fromNMS(CompoundTag nms, me.jishuna.jishlib.nms.nbt.tag.CompoundTag target) {
        for (String key : nms.getAllKeys()) {
            Tag tag = nms.get(key);
            if (tag == null) {
                continue;
            }

            target.put(key, fromNMS(tag));
        }

        return target;
    }

    protected me.jishuna.jishlib.nms.nbt.tag.ListTag fromNMS(ListTag nms, me.jishuna.jishlib.nms.nbt.tag.ListTag target) {
        nms.forEach(tag -> {
            if (tag == null) {
                return;
            }

            target.add(fromNMS(tag));
        });
        return target;
    }

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

    public CompoundTag toNMS(me.jishuna.jishlib.nms.nbt.tag.CompoundTag compound, CompoundTag target) {
        compound.forEach((key, value) -> {
            target.put(key, toNMS(value));
        });

        return target;
    }

    protected ListTag toNMS(me.jishuna.jishlib.nms.nbt.tag.ListTag list, ListTag target) {
        list.forEach(tag -> {
            if (tag == null) {
                return;
            }

            target.add(toNMS(tag));
        });
        return target;
    }

    protected Tag toNMS(NBTTag<?> baseTag) {
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.CompoundTag tag) {
            return toNMS(tag, new CompoundTag());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.ListTag tag) {
            return toNMS(tag, new ListTag());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.ByteTag tag) {
            return ByteTag.valueOf(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.ShortTag tag) {
            return ShortTag.valueOf(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.IntTag tag) {
            return IntTag.valueOf(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.LongTag tag) {
            return LongTag.valueOf(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.FloatTag tag) {
            return FloatTag.valueOf(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.DoubleTag tag) {
            return DoubleTag.valueOf(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.StringTag tag) {
            return StringTag.valueOf(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.ByteArrayTag tag) {
            return new ByteArrayTag(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.IntArrayTag tag) {
            return new IntArrayTag(tag.getValue());
        }
        if (baseTag instanceof me.jishuna.jishlib.nms.nbt.tag.LongArrayTag tag) {
            return new LongArrayTag(tag.getValue());
        }

        if (baseTag != null) {
            System.out.println("Unhandled type: " + baseTag.getClass().getName());
        }
        return null;
    }
}
