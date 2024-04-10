package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class PagedCustomInventory<T, B extends Inventory> extends PagedInventory<T, B> {
    private List<T> items;

    protected PagedCustomInventory(B inventory, Collection<T> items, int maxIndex) {
        this(inventory, new ArrayList<>(items), maxIndex);
    }

    protected PagedCustomInventory(B inventory, List<T> items, int maxIndex) {
        this(inventory, items, IntStream.range(0, maxIndex).boxed().toList());
    }

    protected PagedCustomInventory(B inventory, Collection<T> items, List<Integer> itemSlots) {
        this(inventory, new ArrayList<>(items), itemSlots);
    }

    protected PagedCustomInventory(B inventory, List<T> items, List<Integer> itemSlots) {
        super(inventory, itemSlots, Math.max(0, (int) Math.ceil(items.size() / (double) itemSlots.size()) - 1));
        this.items = items;
    }

    @Override
    protected abstract ItemStack asItemStack(T entry);

    @Override
    protected abstract void onItemClicked(InventoryClickEvent event, InventorySession session, T item);

    @Override
    protected void refreshOptions() {
        int index = this.page * this.itemSlots.size();
        for (int i : this.itemSlots) {
            if (index >= this.items.size()) {
                clearItem(i);
                removeButton(i);
            } else {
                T entry = this.items.get(index);

                ItemStack item = asItemStack(entry);
                setButton(i, item, this::onClick);
            }

            index++;
        }
    }

    protected List<T> getContents() {
        return Collections.unmodifiableList(this.items);
    }

    protected void replaceContents(Collection<T> items) {
        replaceContents(new ArrayList<>(items));
    }

    protected void replaceContents(List<T> items) {
        this.items = items;
        this.maxPage = Math.max(0, (int) Math.ceil(items.size() / (double) this.itemSlots.size()) - 1);
        this.page = 0;

        refreshOptions();
    }

    @Override
    protected void onClick(InventoryClickEvent event, InventorySession session) {
        int startIndex = this.page * this.itemSlots.size();
        T entry = this.items.get(startIndex + this.itemSlots.indexOf(event.getSlot()));

        onItemClicked(event, session, entry);
    }
}
