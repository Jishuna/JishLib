package me.jishuna.jishlib.inventory;

import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.util.NumberUtils;

public abstract class PagedCustomInventory<T, B extends Inventory> extends CustomInventory<B> {
    private List<T> items;
    private final int itemsPerPage;
    private int maxPage;

    private int page = 0;

    protected PagedCustomInventory(B inventory, List<T> items, int maxIndex) {
        super(inventory);
        this.items = items;
        this.itemsPerPage = maxIndex;
        this.maxPage = Math.max(0, (int) Math.ceil(items.size() / (double) maxIndex) - 1);
    }

    protected abstract ItemStack asItemStack(T entry);

    protected void changePage(int amount) {
        int prev = this.page;

        this.page = NumberUtils.clamp(this.page + amount, 0, this.maxPage);
        if (this.page != prev) {
            refreshOptions();
        }
    }

    protected int getPage() {
        return this.page;
    }

    protected abstract void onItemClicked(InventoryClickEvent event, CustomInventorySession session, T item);

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
                setButton(i, item, (event, session) -> onClick(event, session));
            }
        }
    }

    protected void updateList(List<T> items) {
        this.items = items;
        this.maxPage = Math.max(0, (int) Math.ceil(items.size() / (double) this.itemsPerPage) - 1);
        this.page = 0;

        refreshOptions();
    }

    private void onClick(InventoryClickEvent event, CustomInventorySession session) {
        int startIndex = this.page * this.itemsPerPage;
        T entry = this.items.get(startIndex + event.getSlot());

        onItemClicked(event, session, entry);
    }
}
