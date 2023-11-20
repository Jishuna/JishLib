package me.jishuna.jishlib.nms.nbt.tag;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.nms.nbt.CollectionTag;
import me.jishuna.jishlib.nms.nbt.NBTTag;
import me.jishuna.jishlib.nms.nbt.NumericTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class CompoundTag implements CollectionTag<Map<String, NBTTag<?>>> {
    private final Map<String, NBTTag<?>> tagMap;

    public CompoundTag() {
        this.tagMap = new HashMap<>();
    }

    @Override
    public Map<String, NBTTag<?>> getValue() {
        return Collections.unmodifiableMap(this.tagMap);
    }

    public void put(String key, NBTTag<?> value) {
        this.tagMap.put(key, value);
    }

    public void put(String key, byte value) {
        this.tagMap.put(key, new ByteTag(value));
    }

    public void put(String key, short value) {
        this.tagMap.put(key, new ShortTag(value));
    }

    public void put(String key, int value) {
        this.tagMap.put(key, new IntTag(value));
    }

    public void put(String key, long value) {
        this.tagMap.put(key, new LongTag(value));
    }

    public void put(String key, float value) {
        this.tagMap.put(key, new FloatTag(value));
    }

    public void put(String key, double value) {
        this.tagMap.put(key, new DoubleTag(value));
    }

    public void put(String key, String value) {
        this.tagMap.put(key, new StringTag(value));
    }

    public void put(String key, byte[] value) {
        this.tagMap.put(key, new ByteArrayTag(value));
    }

    public void put(String key, int[] value) {
        this.tagMap.put(key, new IntArrayTag(value));
    }

    public void put(String key, long[] value) {
        this.tagMap.put(key, new LongArrayTag(value));
    }

    public NBTTag<?> get(String key) {
        return this.tagMap.get(key);
    }

    public byte getByte(String key) {
        NBTTag<?> tag = this.tagMap.get(key);
        if (tag instanceof NumericTag<?> numeric) {
            return numeric.getAsByte();
        }
        return 0;
    }

    public short getShort(String key) {
        NBTTag<?> tag = this.tagMap.get(key);
        if (tag instanceof NumericTag<?> numeric) {
            return numeric.getAsShort();
        }
        return 0;
    }

    public int getInt(String key) {
        NBTTag<?> tag = this.tagMap.get(key);
        if (tag instanceof NumericTag<?> numeric) {
            return numeric.getAsInt();
        }
        return 0;
    }

    public long getLong(String key) {
        NBTTag<?> tag = this.tagMap.get(key);
        if (tag instanceof NumericTag<?> numeric) {
            return numeric.getAsLong();
        }
        return 0;
    }

    public float getFloat(String key) {
        NBTTag<?> tag = this.tagMap.get(key);
        if (tag instanceof NumericTag<?> numeric) {
            return numeric.getAsFloat();
        }
        return 0;
    }

    public double getDouble(String key) {
        NBTTag<?> tag = this.tagMap.get(key);
        if (tag instanceof NumericTag<?> numeric) {
            return numeric.getAsDouble();
        }
        return 0;
    }

    public boolean has(String key) {
        return this.tagMap.containsKey(key);
    }

    public TagType getType(String key) {
        NBTTag<?> tag = this.tagMap.get(key);
        if (tag == null) {
            return TagType.NONE;
        }
        return tag.getType();
    }

    public NBTTag<?> remove(String key) {
        return this.tagMap.remove(key);
    }

    public void forEach(BiConsumer<String, NBTTag<?>> consumer) {
        this.tagMap.forEach(consumer::accept);
    }

    @Override
    public TagType getType() {
        return TagType.COMPOUND;
    }

    @Override
    public void debug(CommandSender target, int depth) {
        forEach((key, value) -> {
            if (value == null) {
                return;
            }
            if (value instanceof CollectionTag<?> collection) {
                target.sendMessage(" ".repeat(depth) + "Key: " + key);
                collection.debug(target, depth + 2);
                return;
            }

            target.sendMessage(" ".repeat(depth) + "Key: " + key + " | Value: " + value.toString());
        });
    }
}
