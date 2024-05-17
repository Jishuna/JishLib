package me.jishuna.jishlib.inventory;

import java.util.ArrayDeque;
import java.util.Deque;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.util.Tasks;

public final class InventorySession {
    public enum State {
        NORMAL, SWITCHING, WAITING;
    }

    private final Player player;
    private final Deque<CustomInventory<?>> history = new ArrayDeque<>();

    private CustomInventory<?> active;
    private State state = State.NORMAL;

    public InventorySession(Player player, CustomInventory<?> inventory) {
        this.player = player;
        this.active = inventory;
    }

    public void changeTo(CustomInventory<?> inventory, boolean recordHistory) {
        Tasks.run(() -> {
            this.state = State.SWITCHING;
            open(inventory, recordHistory);
            this.state = State.NORMAL;
        });
    }

    public void close() {
        Tasks.run(this.player::closeInventory);
    }

    public void closeAndWait() {
        Tasks.run(() -> {
            this.state = State.WAITING;
            this.player.closeInventory();
        });
    }

    public CustomInventory<?> getActive() {
        return this.active;
    }

    public CustomInventory<?> getPrevious() {
        if (!hasHistory()) {
            return null;
        }

        return this.history.peekFirst();
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
        open(this.active, false);
    }

    public Player getPlayer() {
        return this.player;
    }

    private void open(CustomInventory<?> inventory, boolean recordHistory) {
        if (recordHistory) {
            this.history.addFirst(this.active);
        }

        inventory.open(this.player);
        this.active = inventory;
        this.active.consumeOpenEvent(this);

        if (this.state == State.WAITING) {
            this.state = State.NORMAL;
        }
    }
}
