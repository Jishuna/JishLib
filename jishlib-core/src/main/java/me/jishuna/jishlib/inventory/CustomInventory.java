package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.inventory.button.Button;
import me.jishuna.jishlib.item.ItemstackRepresentable;

public class CustomInventory<T extends Inventory> {
    private final Map<Integer, BiConsumer<InventoryClickEvent, InventorySession>> buttons = new HashMap<>();
    private final Map<Integer, ItemstackRepresentable> providers = new HashMap<>();

    private final List<BiConsumer<InventoryClickEvent, InventorySession>> clickActions = new ArrayList<>();
    private final List<BiConsumer<InventoryCloseEvent, InventorySession>> closeActions = new ArrayList<>();
    private final List<BiConsumer<HumanEntity, InventorySession>> openActions = new ArrayList<>();

    private final T inventory;

    public CustomInventory(T inventory) {
        this.inventory = inventory;
    }

    public void addClickConsumer(BiConsumer<InventoryClickEvent, InventorySession> action) {
        this.clickActions.add(action);
    }

    public void addCloseConsumer(BiConsumer<InventoryCloseEvent, InventorySession> action) {
        this.closeActions.add(action);
    }

    public void addOpenConsumer(BiConsumer<HumanEntity, InventorySession> action) {
        this.openActions.add(action);
    }

    public void cancelAllClicks() {
        this.clickActions.add((event, session) -> event.setCancelled(true));
    }

    public void setItem(int slot, ItemstackRepresentable provider) {
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

    public void replaceItem(int slot, ItemstackRepresentable provider, int ticks) {
        replaceItem(slot, provider.asItemStack(), ticks);
    }

    public void replaceItem(int slot, ItemStack item, int ticks) {
        ItemStack previous = this.inventory.getItem(slot);
        this.inventory.setItem(slot, item);
        JishLib.runLater(() -> {
            ItemStack current = this.inventory.getItem(slot);
            if (current != null && current.isSimilar(item)) {
                this.inventory.setItem(slot, previous);
            }
        }, ticks);
    }

    public void addItem(ItemStack item) {
        this.inventory.addItem(item);
    }

    public void clearItem(int slot) {
        this.inventory.setItem(slot, null);
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public ItemstackRepresentable getProvider(int slot) {
        return this.providers.get(slot);
    }

    public boolean hasItem(int slot) {
        return this.inventory.getItem(slot) != null || this.providers.get(slot) != null;
    }

    public void fillEmpty(ItemStack filler) {
        for (int slot = 0; slot < this.inventory.getSize(); slot++) {
            if (!hasItem(slot)) {
                setItem(slot, filler);
            }
        }
    }

    public void removeButton(int slot) {
        this.buttons.remove(slot);
    }

    public void setButton(int slot, BiConsumer<InventoryClickEvent, InventorySession> action) {
        this.buttons.put(slot, action);
    }

    public void setButton(int slot, Button button) {
        this.buttons.put(slot, button.getAction());
        this.providers.put(slot, button.getProvider());
    }

    public void setButton(int slot, ItemstackRepresentable provider, BiConsumer<InventoryClickEvent, InventorySession> action) {
        this.buttons.put(slot, action);
        this.providers.put(slot, provider);
    }

    public void setButton(int slot, ItemStack item, BiConsumer<InventoryClickEvent, InventorySession> action) {
        this.buttons.put(slot, action);
        this.inventory.setItem(slot, item);
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    public void open(HumanEntity target) {
        InventoryAPI.openInventory(target, this, true);
    }

    protected void openDirect(HumanEntity target) {
        target.openInventory(this.inventory);
    }

    protected void onDiscard(InventorySession session) {
        // For children to override
    }

    @SafeVarargs
    public final void apply(Consumer<T>... consumers) {
        for (Consumer<T> consumer : consumers) {
            consumer.accept(this.inventory);
        }
    }

    public T getBukkitInventory() {
        return this.inventory;
    }

    public final void consumeClickEvent(InventoryClickEvent event, InventorySession session) {
        int slot = event.getRawSlot();

        BiConsumer<InventoryClickEvent, InventorySession> buttonConsumer = this.buttons.get(slot);

        if (buttonConsumer != null) {
            buttonConsumer.accept(event, session);
        }

        this.clickActions.forEach(consumer -> consumer.accept(event, session));
    }

    public final void consumeCloseEvent(InventoryCloseEvent event, InventorySession session) {
        this.closeActions.forEach(consumer -> consumer.accept(event, session));
    }

    public final void consumeOpen(HumanEntity entity, InventorySession session) {
        processProviders();
        this.openActions.forEach(consumer -> consumer.accept(entity, session));
    }

    private void processProviders() {
        for (Entry<Integer, ItemstackRepresentable> entry : this.providers.entrySet()) {
            this.inventory.setItem(entry.getKey(), entry.getValue().asItemStack());
        }
    }

    @Override
    public int hashCode() {
        return this.inventory.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CustomInventory<?> other)) {
            return false;
        }

        return Objects.equals(this.inventory, other.inventory);
    }

}
