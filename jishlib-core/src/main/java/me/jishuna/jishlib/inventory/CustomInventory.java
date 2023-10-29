package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.item.provider.ItemProvider;

public class CustomInventory<T extends Inventory> {
    private final Map<Integer, Consumer<InventoryClickEvent>> buttons = new HashMap<>();

    private final Map<Integer, ItemProvider> providers = new HashMap<>();

    private final List<Consumer<InventoryClickEvent>> clickActions = new ArrayList<>();
    private final List<Consumer<InventoryCloseEvent>> closeActions = new ArrayList<>();
    private final List<Consumer<HumanEntity>> openActions = new ArrayList<>();

    private final T inventory;
    private final CustomInventory<?> previous;

    public CustomInventory(T inventory, CustomInventory<?> previous) {
        this.inventory = inventory;
        this.previous = previous;
    }

    public CustomInventory(T inventory) {
        this(inventory, null);
    }

    public CustomInventory(CustomInventory<T> backing) {
        this(backing.inventory, null);

        this.buttons.putAll(backing.buttons);
        this.clickActions.addAll(backing.clickActions);
        this.closeActions.addAll(backing.closeActions);
        this.openActions.addAll(backing.openActions);
    }

    public void setButton(int slot, Button button) {
        this.buttons.put(slot, event -> button.getAction().accept(event, this));
        this.providers.put(slot, button.getProvider());
    }

    public void setButton(int slot, ItemStack item, Consumer<InventoryClickEvent> action) {
        this.buttons.put(slot, action);
        this.inventory.setItem(slot, item);
    }

    public void setButton(int slot, ItemProvider provider, Consumer<InventoryClickEvent> action) {
        this.buttons.put(slot, action);
        this.providers.put(slot, provider);
    }

    public void setButton(int slot, Consumer<InventoryClickEvent> action) {
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

    public void addOpenConsumer(Consumer<HumanEntity> action) {
        this.openActions.add(action);
    }

    public void addItem(ItemStack item) {
        this.inventory.addItem(item);
    }

    public void setItem(int slot, ItemStack item) {
        this.inventory.setItem(slot, item);
    }

    public void setItem(int slot, ItemProvider provider) {
        this.providers.put(slot, provider);
    }

    public void setItem(ItemStack item, int... slots) {
        for (int i : slots) {
            setItem(i, item);
        }
    }

    public void clearItem(int slot) {
        this.inventory.setItem(slot, null);
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

    public CustomInventory<?> getPrevious() {
        return previous;
    }

    public Inventory getBukkitInventory() {
        return this.inventory;
    }

    public void open(HumanEntity target) {
        JishLib.getInventoryManager().openInventory(target, this);
    }

    public void openSafe(HumanEntity target) {
        Bukkit.getScheduler().runTask(JishLib.getPluginInstance(), () -> open(target));
    }

    public void openPrevious(HumanEntity target) {
        if (this.previous != null) {
            this.previous.openSafe(target);
        }
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

    public final void consumeOpen(HumanEntity player) {
        processProviders();
        this.openActions.forEach(consumer -> consumer.accept(player));
    }

    private void processProviders() {
        for (Entry<Integer, ItemProvider> entry : this.providers.entrySet()) {
            this.inventory.setItem(entry.getKey(), entry.getValue().get());
        }
    }
}
