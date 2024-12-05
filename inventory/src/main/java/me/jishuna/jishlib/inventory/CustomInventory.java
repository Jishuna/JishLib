package me.jishuna.jishlib.inventory;

import me.jishuna.jishlib.ComponentSerializers;
import me.jishuna.jishlib.adapter.Adapter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class CustomInventory {
    private final Map<Integer, Consumer<ClickContext>> buttons = new HashMap<>();

    private final List<Consumer<ClickContext>> clickActions = new ArrayList<>();
    private final List<Consumer<InventorySession>> openActions = new ArrayList<>();
    private final List<Consumer<InventorySession>> closeActions = new ArrayList<>();

    private final Inventory inventory;
    private final Component title;
    private final Set<Integer> edgeSlots = new HashSet<>();
    private final Set<Integer> innerSlots = new HashSet<>();

    public CustomInventory(int size, String title) {
        this(size, ComponentSerializers.LEGACY_SERIALIZER.deserialize(title));
    }

    public CustomInventory(int size, Component title) {
        this.inventory = Bukkit.createInventory(null, size);
        this.title = title;

        calculateSlots();
    }

    public void onClick(Consumer<ClickContext> consumer) {
        this.clickActions.add(consumer);
    }

    public void cancelAllClicks() {
        this.clickActions.add(c -> c.event().setCancelled(true));
    }

    public void onOpen(Consumer<InventorySession> consumer) {
        this.openActions.add(consumer);
    }

    public void onClose(Consumer<InventorySession> consumer) {
        this.closeActions.add(consumer);
    }

    public void setItem(int slot, ItemStack item) {
        this.inventory.setItem(slot, item);
    }

    public void setItem(ItemStack item, int... slots) {
        for (int i : slots) {
            setItem(i, item);
        }
    }

    public void setItem(ItemStack item, Collection<Integer> slots) {
        slots.forEach(i -> setItem(i, item));
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public boolean hasItem(int slot) {
        return this.inventory.getItem(slot) != null;
    }

    public void fillEmpty(ItemStack filler) {
        for (int slot = 0; slot < this.inventory.getSize(); slot++) {
            if (!hasItem(slot)) {
                setItem(slot, filler);
            }
        }
    }

    public void setButton(int slot, Consumer<ClickContext> consumer) {
        this.buttons.put(slot, consumer);
    }

    public void setButton(int slot, ItemStack item, Consumer<ClickContext> consumer) {
        this.buttons.put(slot, consumer);
        setItem(slot, item);
    }

    public void removeButton(int slot) {
        this.buttons.remove(slot);
    }

    public Collection<Integer> getEdgeSlots() {
        return this.edgeSlots;
    }

    public Collection<Integer> getInnerSlots() {
        return this.innerSlots;
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    public Inventory getBukkitInventory() {
        return this.inventory;
    }

    protected final void open(HumanEntity target) {
        Adapter.get().openInventory(target, inventory, title);
    }

    final void consumeClickEvent(ClickContext context) {
        int slot = context.event().getRawSlot();

        Consumer<ClickContext> buttonConsumer = this.buttons.get(slot);

        if (buttonConsumer != null) {
            buttonConsumer.accept(context);
        }

        this.clickActions.forEach(c -> c.accept(context));
    }

    final void consumeOpenEvent(InventorySession session) {
        this.openActions.forEach(c -> c.accept(session));
    }

    final void consumeCloseEvent(InventorySession session) {
        this.closeActions.forEach(c -> c.accept(session));
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

        if (!(obj instanceof CustomInventory other)) {
            return false;
        }

        return Objects.equals(this.inventory, other.inventory);
    }

    private void calculateSlots() {
        int size = getSize();

        for (int slot = 0; slot < size; slot++) {
            if (slot < 9 || slot >= size - 9 || slot % 9 == 0 || slot % 9 == 8) {
                this.edgeSlots.add(slot);
            } else {
                this.innerSlots.add(slot);
            }
        }
    }
}
