package me.jishuna.jishlib.inventory;

import java.util.ArrayDeque;
import java.util.Deque;
import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.JishLib;

public final class CustomInventorySession {
    public enum State {
        NORMAL, SWITCHING, WAITING;
    }

    private final Deque<CustomInventory<?>> history = new ArrayDeque<>();
    private final HumanEntity player;
    private final CustomInventoryManager manager;

    private State state = State.SWITCHING;
    private CustomInventory<?> active;

    public CustomInventorySession(HumanEntity player, CustomInventory<?> first, CustomInventoryManager manager) {
        this.player = player;
        this.active = first;
        this.manager = manager;
    }

    public void changeTo(CustomInventory<?> inventory, boolean recordHistory) {
        JishLib.run(() -> {
            this.state = State.SWITCHING;
            this.manager.openInventory(this.player, inventory, recordHistory);
        });
    }

    public void closeAndWait() {
        JishLib.run(() -> {
            this.state = State.WAITING;
            this.player.closeInventory();
        });
    }

    public CustomInventory<?> getActive() {
        return this.active;
    }

    public State getState() {
        return this.state;
    }

    public boolean hasHistory() {
        return !this.history.isEmpty();
    }

    public void openPrevious() {
        if (!hasHistory()) {
            return;
        }

        changeTo(this.history.pollFirst(), false);
    }

    public void reopen() {
        if (this.active != null) {
            this.manager.openInventory(this.player, this.active, false);
        }
    }

    protected void open(CustomInventory<?> inventory, boolean recordHistory) {
        if (recordHistory && this.active != null) {
            this.history.addFirst(this.active);
        }

        this.active = inventory;
        this.player.openInventory(inventory.getBukkitInventory());

        if (this.state != State.NORMAL) {
            this.state = State.NORMAL;
        }
    }
}
