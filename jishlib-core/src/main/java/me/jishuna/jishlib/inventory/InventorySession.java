package me.jishuna.jishlib.inventory;

import java.util.ArrayDeque;
import java.util.Deque;
import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.JishLib;

public final class InventorySession {
    public enum State {
        NORMAL, SWITCHING, WAITING;
    }

    private final Deque<CustomInventory<?>> history = new ArrayDeque<>();
    private final HumanEntity player;

    private State state = State.SWITCHING;
    private CustomInventory<?> active;

    public InventorySession(HumanEntity player, CustomInventory<?> first) {
        this.player = player;
        this.active = first;
    }

    public void changeTo(CustomInventory<?> inventory, boolean recordHistory) {
        JishLib.run(() -> {
            this.state = State.SWITCHING;
            open(inventory, recordHistory);
        });
    }

    public void close() {
        JishLib.run(this.player::closeInventory);
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
            open(this.active, false);
        }
    }

    public void open(CustomInventory<?> inventory, boolean recordHistory) {
        if (recordHistory && this.active != null) {
            this.history.addFirst(this.active);
        }

        this.active = inventory;
        inventory.openDirect(this.player);

        if (this.state != State.NORMAL) {
            this.state = State.NORMAL;
        }
    }
}
