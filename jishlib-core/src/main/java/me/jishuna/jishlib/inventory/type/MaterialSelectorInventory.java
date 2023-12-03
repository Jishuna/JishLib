package me.jishuna.jishlib.inventory.type;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.inventory.PagedCustomInventory;
import me.jishuna.jishlib.nms.inventory.AnvilGuiInventory;

public class MaterialSelectorInventory extends PagedCustomInventory<Material, Inventory> {
    public static final List<Material> ALL_ITEMS = Arrays
            .stream(Material.values())
            .filter(Material::isItem)
            .filter(Predicate.not(Material::isAir))
            .toList();

    private final BiConsumer<InventorySession, Material> callback;
    private String search = " ";

    private MaterialSelectorInventory(String title, Consumer<MaterialSelectorInventory> populator, BiConsumer<InventorySession, Material> callback) {
        super(Bukkit.createInventory(null, 54, title), ALL_ITEMS, 45);
        this.callback = callback;

        addClickConsumer(event -> event.setCancelled(true));

        populator.accept(this);
        refreshOptions();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected ItemStack asItemStack(Material item) {
        return new ItemStack(item);
    }

    @Override
    protected void onItemClicked(InventoryClickEvent event, InventorySession session, Material item) {
        this.callback.accept(session, item);
    }

    public void search(InventoryClickEvent event, InventorySession session) {
        if (event.isShiftClick()) {
            this.search = " ";
            replaceContents(ALL_ITEMS);
            return;
        }

        AnvilGuiInventory inventory = AnvilGuiInventory
                .builder()
                .inventoryName("Search")
                .initialInput(this.search)
                .onFinished((sess, input) -> {
                    filter(input);
                    sess.openPrevious();
                })
                .create(event.getWhoClicked());

        session.changeTo(inventory, true);
    }

    private void filter(String filter) {
        this.search = filter;
        replaceContents(ALL_ITEMS.stream().filter(mat -> mat.getKey().toString().contains(filter)).toList());
    }

    public static class Builder {
        private String title = "Select Material";
        private Consumer<MaterialSelectorInventory> populator = p -> {
        };
        private BiConsumer<InventorySession, Material> callback = (s, i) -> {
        };

        private Builder() {
        }

        public Builder inventoryName(String name) {
            this.title = name;
            return this;
        }

        public Builder populator(Consumer<MaterialSelectorInventory> populator) {
            this.populator = populator;
            return this;
        }

        public Builder onFinished(BiConsumer<InventorySession, Material> callback) {
            this.callback = callback;
            return this;
        }

        public MaterialSelectorInventory create() {
            return new MaterialSelectorInventory(this.title, this.populator, this.callback);
        }
    }
}
