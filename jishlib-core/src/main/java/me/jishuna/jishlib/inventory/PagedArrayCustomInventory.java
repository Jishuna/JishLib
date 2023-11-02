package me.jishuna.jishlib.inventory;

import java.util.Arrays;
import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.util.NumberUtils;

public abstract class PagedArrayCustomInventory<T, B extends Inventory> extends CustomInventory<B> {
    private final List<T[]> items;
    private final int itemsPerPage;
    private final int maxPage;

    private int page = 0;

    protected PagedArrayCustomInventory(B inventory, List<T[]> items, int maxIndex) {
        super(inventory);
        this.items = items;
        this.itemsPerPage = maxIndex;
        this.maxPage = this.items.size() - 1;
    }

    protected abstract ItemStack asItemStack(T entry);

    protected void changePage(int amount) {
        this.page = NumberUtils.clamp(this.page + amount, 0, this.maxPage);

        refreshOptions();
    }

    protected int getPage() {
        return this.page;
    }

    protected abstract void onItemClicked(InventoryClickEvent event, CustomInventorySession session, T item);

    protected void refreshOptions() {
        T[] entry = this.items.get(this.page);
        ItemStack[] stackArray = Arrays.stream(entry).map(this::asItemStack).toArray(ItemStack[]::new);

        for (int i = 0; i < this.itemsPerPage; i++) {
            if (i >= stackArray.length) {
                clearItem(i);
                removeButton(i);
            } else {
                setButton(i, stackArray[i], (event, session) -> onClick(event, session));
            }
        }
    }

    private void onClick(InventoryClickEvent event, CustomInventorySession session) {
        T[] entry = this.items.get(this.page);
        onItemClicked(event, session, entry[event.getSlot()]);
    }
}
