package me.jishuna.jishlib.inventory;

import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.datastructure.History;

public final class InventorySession {
    public enum State {
        NORMAL, SWITCHING, WAITING;
    }

    private final HumanEntity player;
    private final History<CustomInventory<?>> history;

    private State state = State.SWITCHING;

    public InventorySession(HumanEntity player, CustomInventory<?> first) {
        this.player = player;
        this.history = new History<>(first);
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
        return this.history.getActive();
    }

    public State getState() {
        return this.state;
    }

    public boolean hasHistory() {
        return this.history.hasPrevious();
    }

    public void openPrevious() {
        if (!hasHistory()) {
            return;
        }

        if (this.history.getActive() != null) {
            this.history.getActive().onDiscard(this);
        }
        changeTo(this.history.pollPrevious(), false);
    }

    public void reopen() {
        if (this.history.getActive() != null) {
            open(this.history.getActive(), false);
        }
    }

    protected final void onDiscard() {
        if (this.history.getActive() != null) {
            this.history.getActive().onDiscard(this);
        }

        while (this.history.hasPrevious()) {
            this.history.pollPrevious().onDiscard(this);
        }
    }

    public void open(CustomInventory<?> inventory, boolean recordHistory) {
        if (this.history.getActive() != null) {
            this.history.getActive().onDiscard(this);
        }

        inventory.openDirect(this.player);
        this.history.setActive(inventory, recordHistory);
        inventory.consumeOpen(this.player, this);

        if (this.state != State.NORMAL) {
            this.state = State.NORMAL;
        }
    }

    public HumanEntity getPlayer() {
        return this.player;
    }
}
