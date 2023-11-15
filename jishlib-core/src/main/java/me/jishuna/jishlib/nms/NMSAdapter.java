package me.jishuna.jishlib.nms;

import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.nms.inventory.CustomAnvilMenu;

public interface NMSAdapter {
    public CustomAnvilMenu createAnvilMenu(HumanEntity player, String title);
}
