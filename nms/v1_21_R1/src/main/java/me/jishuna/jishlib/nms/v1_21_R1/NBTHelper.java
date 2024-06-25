package me.jishuna.jishlib.nms.v1_21_R1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import me.jishuna.jishlib.data.holder.BooleanDataHolder;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ArrayDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;

public class NBTHelper {

    public static MapDataHolder fromCompound(CompoundTag compoundTag) {
        Map<String, DataHolder<?>> map = new LinkedHashMap<>();
        for (String key : compoundTag.getAllKeys()) {
            Tag tag = compoundTag.get(key);
            DataHolder<?> holder = fromTag(tag);
            if (holder != null) {
                holder.setName(key);
                map.put(key, holder);
            }
        }

        return MapDataHolder.of(map);
    }

    public static CompoundTag toCompound(MapDataHolder holder) {
        CompoundTag compoundTag = new CompoundTag();
        holder.forEach((k, v) -> {
            Tag tag = toTag(v);
            if (tag != null) {
                compoundTag.put(k, tag);
            }
        });

        return compoundTag;
    }

    public static ListDataHolder fromList(CollectionTag<?> listTag, Function<List<DataHolder<?>>, ListDataHolder> factory) {
        List<DataHolder<?>> list = new ArrayList<>();
        for (Tag tag : listTag) {
            DataHolder<?> holder = fromTag(tag);
            if (holder != null) {
                list.add(holder);
            }
        }

        return factory.apply(list);
    }

    public static <T extends Tag> CollectionTag<T> toList(ListDataHolder holder, CollectionTag<T> listTag, Class<T> clazz) {
        holder.forEach(h -> {
            Tag tag = toTag(h);
            if (clazz.isInstance(tag)) {
                listTag.add(clazz.cast(tag));
            }
        });

        return listTag;
    }

    public static DataHolder<?> fromTag(Tag nbtTag) {
        if (nbtTag instanceof CompoundTag tag) {
            return fromCompound(tag);
        }

        if (nbtTag instanceof ListTag tag) {
            return fromList(tag, ListDataHolder::of);
        }

        if (nbtTag instanceof NumericTag tag) {
            return NumericDataHolder.of(tag.getAsNumber());
        }

        if (nbtTag instanceof StringTag tag) {
            return StringDataHolder.of(tag.getAsString());
        }

        if (nbtTag instanceof CollectionTag<?> tag) {
            return fromList(tag, ArrayDataHolder::of);
        }

        return null;
    }

    public static Tag toTag(DataHolder<?> dataHolder) {
        if (dataHolder instanceof MapDataHolder holder) {
            return toCompound(holder);
        }

        if (dataHolder instanceof ArrayDataHolder holder) {
            int size = holder.get().size();
            return switch (holder.getType()) {
            case BYTE_ARRAY -> toList(holder, new ByteArrayTag(new byte[size]), ByteTag.class);
            case INT_ARRAY -> toList(holder, new IntArrayTag(new int[size]), IntTag.class);
            case LONG_ARRAY -> toList(holder, new LongArrayTag(new long[size]), LongTag.class);
            default -> null;
            };
        }

        if (dataHolder instanceof ListDataHolder holder) {
            return toList(holder, new ListTag(), Tag.class);
        }

        if (dataHolder instanceof NumericDataHolder<?> holder) {
            return switch (holder.getType()) {
            case BYTE -> ByteTag.valueOf(holder.byteValue());
            case SHORT -> ShortTag.valueOf(holder.shortValue());
            case INT -> IntTag.valueOf(holder.intValue());
            case LONG -> LongTag.valueOf(holder.longValue());
            case FLOAT -> FloatTag.valueOf(holder.floatValue());
            case DOUBLE -> DoubleTag.valueOf(holder.doubleValue());
            default -> null;
            };
        }

        if (dataHolder instanceof StringDataHolder holder) {
            return StringTag.valueOf(holder.get());
        }

        if (dataHolder instanceof BooleanDataHolder holder) {
            return ByteTag.valueOf(holder.get());
        }

        return null;
    }

    private NBTHelper() {
    }
}
