package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class PagedArrayCustomInventory<T, B extends Inventory> extends PagedInventory<T, B> {
    private List<T[]> items;

    protected PagedArrayCustomInventory(B inventory, Collection<T[]> items, int maxIndex) {
        this(inventory, new ArrayList<>(items), maxIndex);
    }

    protected PagedArrayCustomInventory(B inventory, List<T[]> items, int maxIndex) {
        super(inventory, maxIndex, items.size() - 1);
        this.items = items;
    }

    @Override
    protected abstract ItemStack asItemStack(T entry);

    @Override
    protected abstract void onItemClicked(InventoryClickEvent event, InventorySession session, T item);

    @Override
    protected void refreshOptions() {
        T[] entry = this.items.get(this.page);
        ItemStack[] stackArray = Arrays.stream(entry).map(this::asItemStack).toArray(ItemStack[]::new);

        for (int i = 0; i < this.itemsPerPage; i++) {
            if (i >= stackArray.length) {
                clearItem(i);
                removeButton(i);
            } else {
                setButton(i, stackArray[i], this::onClick);
            }
        }
    }

    protected List<T[]> getContents() {
        return Collections.unmodifiableList(this.items);
    }

    protected void replaceContents(List<T[]> items) {
        this.items = items;
        this.maxPage = items.size() - 1;
        this.page = 0;

        refreshOptions();
    }

    @Override
    protected void onClick(InventoryClickEvent event, InventorySession session) {
        T[] entry = this.items.get(this.page);
        onItemClicked(event, session, entry[event.getSlot()]);
    }
}
