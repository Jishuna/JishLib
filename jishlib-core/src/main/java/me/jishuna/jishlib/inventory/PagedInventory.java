package me.jishuna.jishlib.inventory;

import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.util.NumberUtils;

public abstract class PagedInventory<T, B extends Inventory> extends CustomInventory<B> {
    protected final List<Integer> itemSlots;

    protected int maxPage;
    protected int page = 0;

    protected PagedInventory(B inventory, List<Integer> itemSlots, int maxPage) {
        super(inventory);
        this.itemSlots = itemSlots;
        this.maxPage = maxPage;
    }

    protected abstract ItemStack asItemStack(T entry);

    protected abstract void onItemClicked(InventoryClickEvent event, InventorySession session, T item);

    protected abstract void refreshOptions();

    protected abstract void onClick(InventoryClickEvent event, InventorySession session);

    public void changePage(int amount) {
        int prev = this.page;

        this.page = NumberUtils.clamp(this.page + amount, 0, this.maxPage);
        if (this.page != prev) {
            refreshOptions();
        }
    }

    protected int getPage() {
        return this.page;
    }

    protected int getMaxPage() {
        return this.maxPage;
    }
}
