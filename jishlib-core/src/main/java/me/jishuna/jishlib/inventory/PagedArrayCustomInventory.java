package me.jishuna.jishlib.inventory;

import me.jishuna.jishlib.util.Utils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public abstract class PagedArrayCustomInventory<T> extends CustomInventory{
    private final List<T[]> items;

    private int page = 0;

    protected PagedArrayCustomInventory(Inventory inventory, List<T[]> items) {
        super(inventory);
        this.items = items;
    }

    protected abstract ItemStack asItemStack(T entry);

    protected abstract void onItemClicked(InventoryClickEvent event, T item);

    protected void refreshOptions() {
        for (int i = 0; i < 45; i++) {
            T[] entry = this.items.get(page);
            ItemStack[] stackArray = Arrays.stream(entry).map(this::asItemStack).toArray(ItemStack[]::new);
            addButton(i, stackArray[i], this::onClick);
        }
    }

    protected void changePage(int amount) {
        int maxPage = this.items.size();
        this.page = Utils.clamp(this.page + amount, 0, maxPage);

        refreshOptions();
    }

    private void onClick(InventoryClickEvent event) {
        T[] entry = this.items.get(this.page);
        onItemClicked(event, entry[event.getSlot()]);
    }

    protected int getPage() {
        return this.page;
    }
}
