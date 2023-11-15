package me.jishuna.jishlib.nms.base;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.nms.NMSAdapter;
import me.jishuna.jishlib.nms.inventory.CustomAnvilMenu;

public class NMSAdapterImpl implements NMSAdapter {

    @Override
    public CustomAnvilMenu createAnvilMenu(HumanEntity player, String title) {
        ServerPlayer nmsPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(player.getUniqueId());
        return new CustomAnvil(nmsPlayer, Component.literal(title));
    }
}
