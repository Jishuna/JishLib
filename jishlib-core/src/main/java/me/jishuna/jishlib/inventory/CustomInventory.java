package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.item.provider.ItemProvider;

public class CustomInventory<T extends Inventory> {
    private final Map<Integer, BiConsumer<InventoryClickEvent, CustomInventorySession>> buttons = new HashMap<>();
    private final Map<Integer, ItemProvider> providers = new HashMap<>();

    private final List<BiConsumer<InventoryClickEvent, CustomInventorySession>> clickActions = new ArrayList<>();
    private final List<BiConsumer<InventoryCloseEvent, CustomInventorySession>> closeActions = new ArrayList<>();
    private final List<BiConsumer<InventoryOpenEvent, CustomInventorySession>> openActions = new ArrayList<>();

    private final T inventory;

    public CustomInventory(T inventory) {
        this.inventory = inventory;
    }

    public void addClickConsumer(Consumer<InventoryClickEvent> action) {
        this.clickActions.add((event, session) -> action.accept(event));
    }

    public void addCloseConsumer(BiConsumer<InventoryCloseEvent, CustomInventorySession> action) {
        this.closeActions.add(action);
    }

    public void addItem(ItemStack item) {
        this.inventory.addItem(item);
    }

    public void addOpenConsumer(BiConsumer<InventoryOpenEvent, CustomInventorySession> action) {
        this.openActions.add(action);
    }

    @SafeVarargs
    public final void apply(Consumer<T>... consumers) {
        for (Consumer<T> consumer : consumers) {
            consumer.accept(this.inventory);
        }
    }

    public void clearItem(int slot) {
        this.inventory.setItem(slot, null);
    }

    public final void consumeClickEvent(InventoryClickEvent event, CustomInventorySession session) {
        int slot = event.getRawSlot();

        BiConsumer<InventoryClickEvent, CustomInventorySession> buttonConsumer = this.buttons.get(slot);

        if (buttonConsumer != null) {
            buttonConsumer.accept(event, session);
        }

        this.clickActions.forEach(consumer -> consumer.accept(event, session));
    }

    public final void consumeCloseEvent(InventoryCloseEvent event, CustomInventorySession session) {
        this.closeActions.forEach(consumer -> consumer.accept(event, session));
    }

    public final void consumeOpenEvent(InventoryOpenEvent event, CustomInventorySession session) {
        processProviders();
        this.openActions.forEach(consumer -> consumer.accept(event, session));
    }

    public void fillEmpty(ItemStack filler) {
        for (int slot = 0; slot < this.inventory.getSize(); slot++) {
            if (this.inventory.getItem(slot) == null) {
                setItem(slot, filler);
            }
        }
    }

    public Inventory getBukkitInventory() {
        return this.inventory;
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public ItemProvider getProvider(int slot) {
        return this.providers.get(slot);
    }

    public boolean hasItem(int slot) {
        return this.inventory.getItem(slot) != null || this.providers.get(slot) != null;
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    public void open(HumanEntity target) {
        JishLib.getInventoryManager().openInventory(target, this, true);
    }

    public void removeButton(int slot) {
        this.buttons.remove(slot);
    }

    public void setButton(int slot, BiConsumer<InventoryClickEvent, CustomInventorySession> action) {
        this.buttons.put(slot, action);
    }

    public void setButton(int slot, Button button) {
        this.buttons.put(slot, button.getAction());
        this.providers.put(slot, button.getProvider());
    }

    public void setButton(int slot, ItemProvider provider, BiConsumer<InventoryClickEvent, CustomInventorySession> action) {
        this.buttons.put(slot, action);
        this.providers.put(slot, provider);
    }

    public void setButton(int slot, ItemStack item, BiConsumer<InventoryClickEvent, CustomInventorySession> action) {
        this.buttons.put(slot, action);
        this.inventory.setItem(slot, item);
    }

    public void setItem(int slot, ItemProvider provider) {
        this.providers.put(slot, provider);
    }

    public void setItem(int slot, ItemStack item) {
        this.inventory.setItem(slot, item);
    }

    public void setItem(ItemStack item, int... slots) {
        for (int i : slots) {
            setItem(i, item);
        }
    }

    private void processProviders() {
        for (Entry<Integer, ItemProvider> entry : this.providers.entrySet()) {
            this.inventory.setItem(entry.getKey(), entry.getValue().get());
        }
    }
}
