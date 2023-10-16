package me.jishuna.jishlib.inventory;

import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.util.Utils;

public abstract class PagedCustomInventory<T, B extends Inventory> extends CustomInventory<B> {
    private List<T> items;
    private final int itemsPerPage;
    private int maxPage;

    private int page = 0;

    protected PagedCustomInventory(B inventory, List<T> items, int maxIndex, CustomInventory<?> previous) {
        super(inventory, previous);
        this.items = items;
        this.itemsPerPage = maxIndex;
        this.maxPage = Math.max(0, (int) Math.ceil(items.size() / (double) maxIndex) - 1);
    }

    protected abstract ItemStack asItemStack(T entry);

    protected abstract void onItemClicked(InventoryClickEvent event, T item);

    protected void refreshOptions() {
        int startIndex = this.page * this.itemsPerPage;
        for (int i = 0; i < this.itemsPerPage; i++) {
            int index = startIndex + i;

            if (index >= this.items.size()) {
                setItem(i, null);
                removeButton(i);
            } else {
                T entry = this.items.get(index);

                ItemStack item = asItemStack(entry);
                addButton(i, item, this::onClick);
            }
        }
    }

    protected void changePage(int amount) {
        int prev = this.page;

        this.page = Utils.clamp(this.page + amount, 0, this.maxPage);
        if (this.page != prev) {
            refreshOptions();
        }
    }

    protected int getPage() {
        return this.page;
    }

    protected void updateList(List<T> items) {
        this.items = items;
        this.maxPage = Math.max(0, (int) Math.ceil(items.size() / (double) this.itemsPerPage) - 1);

        refreshOptions();
    }

    private void onClick(InventoryClickEvent event) {
        int startIndex = this.page * this.itemsPerPage;
        T entry = this.items.get(startIndex + event.getSlot());

        onItemClicked(event, entry);
    }
}
