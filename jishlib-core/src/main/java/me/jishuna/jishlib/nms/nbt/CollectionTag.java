package me.jishuna.jishlib.nms.nbt;

import org.bukkit.command.CommandSender;

public interface CollectionTag<T> extends NBTTag<T> {
    public void debug(CommandSender target, int depth);
}
