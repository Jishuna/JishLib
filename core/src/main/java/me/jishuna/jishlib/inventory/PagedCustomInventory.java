package me.jishuna.jishlib.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public abstract class PagedCustomInventory<T> extends PagedInventory<T> {
    private List<T> items;

    protected PagedCustomInventory(int size, Component title, Collection<T> items, int maxIndex) {
        this(size, title, new ArrayList<>(items), maxIndex);
    }

    protected PagedCustomInventory(int size, Component title, List<T> items, int maxIndex) {
        this(size, title, items, IntStream.range(0, maxIndex).boxed().toList());
    }

    protected PagedCustomInventory(int size, Component title, Collection<T> items, List<Integer> itemSlots) {
        this(size, title, new ArrayList<>(items), itemSlots);
    }

    protected PagedCustomInventory(int size, Component title, List<T> items, List<Integer> itemSlots) {
        super(size, title, itemSlots, Math.max(0, (int) Math.ceil(items.size() / (double) itemSlots.size()) - 1));
        this.items = items;
    }

    @Override
    protected abstract ItemStack asItemStack(T entry);

    @Override
    protected void refreshOptions() {
        int index = this.page * this.itemSlots.size();
        for (int i : this.itemSlots) {
            if (index >= this.items.size()) {
                setItem(i, (ItemStack) null);
                removeButton(i);
            } else {
                T entry = this.items.get(index);

                ItemStack item = asItemStack(entry);
                setButton(i, item, this::onClick);
            }

            index++;
        }
    }

    protected List<T> getContents() {
        return Collections.unmodifiableList(this.items);
    }

    protected void replaceContents(Collection<T> items) {
        replaceContents(new ArrayList<>(items));
    }

    protected void replaceContents(List<T> items) {
        this.items = items;
        this.maxPage = Math.max(0, (int) Math.ceil(items.size() / (double) this.itemSlots.size()) - 1);
        this.page = 0;

        refreshOptions();
    }

    @Override
    protected void onClick(ClickContext context) {
        int startIndex = this.page * this.itemSlots.size();
        T entry = this.items.get(startIndex + this.itemSlots.indexOf(context.event().getSlot()));

        onItemClicked(context, entry);
    }
}
