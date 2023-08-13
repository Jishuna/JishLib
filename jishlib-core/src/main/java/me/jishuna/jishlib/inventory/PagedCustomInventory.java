package me.jishuna.jishlib.inventory;

import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.util.Utils;

public abstract class PagedCustomInventory<T> extends CustomInventory {
    private final List<T> items;
    private final int maxIndex;

    private int startIndex = 0;

    protected PagedCustomInventory(Inventory inventory, List<T> items, int maxIndex) {
        super(inventory);
        this.items = items;
        this.maxIndex = maxIndex;
    }

    protected abstract ItemStack asItemStack(T entry);

    protected abstract void onItemClicked(InventoryClickEvent event, T item);

    protected void refreshOptions() {
        for (int i = 0; i < this.maxIndex; i++) {
            int index = this.startIndex + i;
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
        int maxPage = (this.items.size() - 1) / this.maxIndex;
        this.startIndex = Utils.clamp((this.startIndex / this.maxIndex) + amount, 0, maxPage) * this.maxIndex;

        refreshOptions();
    }

    private void onClick(InventoryClickEvent event) {
        T entry = this.items.get(this.startIndex + event.getSlot());
        onItemClicked(event, entry);
    }

}
