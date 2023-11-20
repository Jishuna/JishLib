package me.jishuna.jishlib.nms.latest;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import me.jishuna.jishlib.nms.inventory.AnvilGuiInventory;
import me.jishuna.jishlib.nms.inventory.CustomAnvilMenu;

public class CustomAnvil extends AnvilMenu implements CustomAnvilMenu {
    private AnvilGuiInventory callbackTarget;

    public CustomAnvil(ServerPlayer player, Component title) {
        super(player.nextContainerCounter(), player.getInventory(), ContainerLevelAccess.create(player.level(), new BlockPos(0, 0, 0)));
        this.checkReachable = false;
        setTitle(title);
    }

    @Override
    public boolean setItemName(String newItemName) {
        this.callbackTarget.onInputTextChange(newItemName);
        this.itemName = newItemName;
        return true;
    }

    @Override
    public void createResult() {
        // Don't let Minecraft overwrite our stuff
    }

    @Override
    public void update() {
        sendAllDataToRemote();
        broadcastChanges();
    }

    @Override
    public void setCallbackTarget(AnvilGuiInventory target) {
        this.callbackTarget = target;
    }

    @Override
    public Inventory getInventory() {
        return getBukkitView().getTopInventory();
    }

    @Override
    public InventoryView getView() {
        return getBukkitView();
    }
}
