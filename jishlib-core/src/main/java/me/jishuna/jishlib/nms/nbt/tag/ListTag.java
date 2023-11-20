package me.jishuna.jishlib.nms.nbt.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.nms.nbt.CollectionTag;
import me.jishuna.jishlib.nms.nbt.NBTTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class ListTag implements CollectionTag<List<NBTTag<?>>> {
    private final List<NBTTag<?>> list;

    public ListTag() {
        this.list = new ArrayList<>();
    }

    public void add(NBTTag<?> tag) {
        this.list.add(tag);
    }

    public void forEach(Consumer<NBTTag<?>> consumer) {
        this.list.forEach(consumer::accept);
    }

    @Override
    public List<NBTTag<?>> getValue() {
        return Collections.unmodifiableList(this.list);
    }

    @Override
    public TagType getType() {
        return TagType.LIST;
    }

    @Override
    public void debug(CommandSender target, int depth) {
        forEach(value -> {
            if (value == null) {
                return;
            }

            if (value instanceof CollectionTag<?> collection) {
                collection.debug(target, depth + 2);
                return;
            }

            target.sendMessage(" ".repeat(depth) + "Value: " + value.toString());
        });
    }

}
