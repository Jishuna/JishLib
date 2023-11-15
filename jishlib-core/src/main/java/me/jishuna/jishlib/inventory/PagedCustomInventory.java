package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class PagedCustomInventory<T, B extends Inventory> extends PagedInventory<T, B> {
    private List<T> items;

    protected PagedCustomInventory(B inventory, Collection<T> items, int maxIndex) {
        this(inventory, new ArrayList<>(items), maxIndex);
    }

    protected PagedCustomInventory(B inventory, List<T> items, int maxIndex) {
        super(inventory, maxIndex, Math.max(0, (int) Math.ceil(items.size() / (double) maxIndex) - 1));
        this.items = items;
    }

    @Override
    protected abstract ItemStack asItemStack(T entry);

    @Override
    protected abstract void onItemClicked(InventoryClickEvent event, InventorySession session, T item);

    @Override
    protected void refreshOptions() {
        int startIndex = this.page * this.itemsPerPage;
        for (int i = 0; i < this.itemsPerPage; i++) {
            int index = startIndex + i;

            if (index >= this.items.size()) {
                clearItem(i);
                removeButton(i);
            } else {
                T entry = this.items.get(index);

                ItemStack item = asItemStack(entry);
                setButton(i, item, this::onClick);
            }
        }
    }

    protected Collection<T> getContents() {
        return Collections.unmodifiableList(this.items);
    }

    protected void replaceContents(List<T> items) {
        this.items = items;
        this.maxPage = Math.max(0, (int) Math.ceil(items.size() / (double) this.itemsPerPage) - 1);
        this.page = 0;

        refreshOptions();
    }

    @Override
    protected void onClick(InventoryClickEvent event, InventorySession session) {
        int startIndex = this.page * this.itemsPerPage;
        T entry = this.items.get(startIndex + event.getSlot());

        onItemClicked(event, session, entry);
    }
}
