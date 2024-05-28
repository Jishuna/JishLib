package me.jishuna.jishlib.inventory;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.util.NumberUtils;

public abstract class PagedInventory<T> extends CustomInventory {
    protected final List<Integer> itemSlots;

    protected int maxPage;
    protected int page = 0;

    protected PagedInventory(int size, Component title, List<Integer> itemSlots, int maxPage) {
        super(size, title);
        this.itemSlots = itemSlots;
        this.maxPage = maxPage;
    }

    protected abstract ItemStack asItemStack(T entry);

    protected abstract void onItemClicked(ClickContext context, T item);

    protected abstract void refreshOptions();

    protected abstract void onClick(ClickContext context);

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
