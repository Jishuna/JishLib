package me.jishuna.jishlib.nms.inventory;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.nms.NMSAPI;
import me.jishuna.jishlib.util.StringUtils;

public class AnvilGuiInventory extends CustomInventory<AnvilInventory> {
    private final CustomAnvilMenu menu;
    private final Predicate<String> validator;
    private final BiConsumer<InventorySession, String> callback;

    private boolean valid;
    private String input;

    private AnvilGuiInventory(CustomAnvilMenu menu, String input, Material inputType, Predicate<String> validator, BiConsumer<InventorySession, String> callback) {
        super((AnvilInventory) menu.getInventory());
        this.menu = menu;
        this.validator = validator;
        this.callback = callback;

        menu.setCallbackTarget(this);

        addClickConsumer(event -> event.setCancelled(true));
        addCloseConsumer((event, session) -> clearItem(0));

        onInputTextChange(input);
        setButton(0, ItemBuilder.create(inputType).name(input).build(), (event, session) -> this.menu.update());
        setButton(2, this::outputClicked);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void onInputTextChange(String newInput) {
        this.input = newInput;
        this.valid = checkValid(this.input);

        if (this.valid) {
            setItem(2, ItemBuilder.create(Material.PAPER).name(StringUtils.miniMessageToLegacy(this.input)).build());
        } else {
            setItem(2, ItemBuilder.create(Material.BARRIER).name(ChatColor.RED + "Invalid Input").build());
        }
        this.menu.update();
    }

    private void outputClicked(InventoryClickEvent event, InventorySession session) {
        if (this.valid) {
            this.callback.accept(session, this.input);
        }
    }

    private boolean checkValid(String input) {
        return this.validator.test(input);
    }

    @Override
    public void openDirect(HumanEntity target) {
        target.openInventory(this.menu.getView());
    }

    public static class Builder {
        private String input = "";
        private String title = "Anvil";
        private Material inputType = Material.PAPER;
        private Predicate<String> validator = s -> true;
        private BiConsumer<InventorySession, String> callback = (s, i) -> {
        };

        private Builder() {
        }

        public Builder initialInput(String input) {
            this.input = input;
            return this;
        }

        public Builder inputType(Material material) {
            if (material != null && !material.isAir()) {
                this.inputType = material;
            }

            return this;
        }

        public Builder inventoryName(String name) {
            this.title = name;
            return this;
        }

        public Builder inputValidator(Predicate<String> validator) {
            this.validator = validator;
            return this;
        }

        public Builder onFinished(BiConsumer<InventorySession, String> callback) {
            this.callback = callback;
            return this;
        }

        public AnvilGuiInventory create(HumanEntity player) {
            CustomAnvilMenu menu = NMSAPI.getAdapter().createAnvilMenu(player, this.title);
            return new AnvilGuiInventory(menu, this.input, this.inputType, this.validator, this.callback);
        }
    }
}
