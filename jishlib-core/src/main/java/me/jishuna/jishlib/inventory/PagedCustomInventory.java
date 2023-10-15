package me.jishuna.jishlib.inventory;

import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.util.Utils;

public abstract class PagedCustomInventory<T, B extends Inventory> extends CustomInventory<B> {
    private final List<T> items;
    private final int itemsPerPage;
    private final int maxPage;

    private int page = 0;

    protected PagedCustomInventory(B inventory, List<T> items, int maxIndex) {
        super(inventory);
        this.items = items;
        this.itemsPerPage = maxIndex;
        this.maxPage = (int) Math.ceil(items.size() / (double) maxIndex);
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
        this.page = Utils.clamp(this.page + amount, 0, this.maxPage);
        refreshOptions();
    }

    protected int getPage() {
        return this.page;
    }

    private void onClick(InventoryClickEvent event) {
        int startIndex = this.page * this.itemsPerPage;
        T entry = this.items.get(startIndex + event.getSlot());

        onItemClicked(event, entry);
    }
}
