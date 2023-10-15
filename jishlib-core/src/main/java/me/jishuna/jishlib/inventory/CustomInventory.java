package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class CustomInventory<T extends Inventory> {
    private final Map<Integer, Consumer<InventoryClickEvent>> buttons = new HashMap<>();
    private final List<Consumer<InventoryClickEvent>> clickActions = new ArrayList<>();
    private final List<Consumer<InventoryCloseEvent>> closeActions = new ArrayList<>();
    private final List<Consumer<InventoryOpenEvent>> openActions = new ArrayList<>();

    private T inventory;

    public CustomInventory(T inventory) {
        this.inventory = inventory;
    }

    public CustomInventory(CustomInventory<T> backing) {
        this.inventory = backing.inventory;

        this.buttons.putAll(backing.buttons);
        this.clickActions.addAll(backing.clickActions);
        this.closeActions.addAll(backing.closeActions);
        this.openActions.addAll(backing.openActions);
    }

    public void addButton(int slot, ItemStack item, Consumer<InventoryClickEvent> action) {
        this.buttons.put(slot, action);
        this.inventory.setItem(slot, item);
    }

    public void addButton(int slot, Consumer<InventoryClickEvent> action) {
        this.buttons.put(slot, action);
    }

    public void removeButton(int slot) {
        this.buttons.remove(slot);
    }

    public void addClickConsumer(Consumer<InventoryClickEvent> action) {
        this.clickActions.add(action);
    }

    public void addCloseConsumer(Consumer<InventoryCloseEvent> action) {
        this.closeActions.add(action);
    }

    public void addOpenConsumer(Consumer<InventoryOpenEvent> action) {
        this.openActions.add(action);
    }

    public void addItem(ItemStack item) {
        this.inventory.addItem(item);
    }

    public void setItem(int slot, ItemStack item) {
        this.inventory.setItem(slot, item);
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public void fillEmpty(ItemStack filler) {
        for (int slot = 0; slot < this.inventory.getSize(); slot++) {
            if (this.inventory.getItem(slot) == null) {
                setItem(slot, filler);
            }
        }
    }

    @SafeVarargs
    public final void apply(Consumer<T>... consumers) {
        for (Consumer<T> consumer : consumers) {
            consumer.accept(this.inventory);
        }
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    protected InventoryView open(HumanEntity target) {
        return target.openInventory(this.inventory);
    }

    public final void consumeClickEvent(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        Consumer<InventoryClickEvent> buttonConsumer = this.buttons.get(slot);

        if (buttonConsumer != null)
            buttonConsumer.accept(event);

        this.clickActions.forEach(consumer -> consumer.accept(event));
    }

    public final void consumeCloseEvent(InventoryCloseEvent event) {
        this.closeActions.forEach(consumer -> consumer.accept(event));
    }

    public final void consumeOpenEvent(InventoryOpenEvent event) {
        this.openActions.forEach(consumer -> consumer.accept(event));
    }
}
