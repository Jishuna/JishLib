package me.jishuna.jishlib.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.profile.PlayerProfile;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.nms.NMS;
import me.jishuna.jishlib.util.Components;
import me.jishuna.jishlib.util.EffectBuilder;
import me.jishuna.jishlib.util.MinecraftVersion;
import me.jishuna.jishlib.util.Utils;

public class ItemBuilder implements ItemSupplier {
    private static final boolean ITEM_COMPONENTS = MinecraftVersion.CURRENT_VERSION.newerThanOrEqual(MinecraftVersion.MC1_20_5);

    private ItemStack item;
    private ItemMeta meta;

    private ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(new ItemStack(material, amount));
    }

    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item);
    }

    public <T extends ItemMeta> ItemBuilder modify(Class<T> metaType, Consumer<T> action) {
        if (!metaType.isAssignableFrom(this.meta.getClass())) {
            return this;
        }

        action.accept(metaType.cast(this.meta));
        return this;
    }

    public Material type() {
        return this.item.getType();
    }

    public ItemBuilder type(Material type) {
        this.item.setType(type);
        this.meta = Bukkit.getItemFactory().asMetaFor(this.meta, type);
        return this;
    }

    public int amount() {
        return this.item.getAmount();
    }

    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public String name() {
        return this.meta.getDisplayName();
    }

    public ItemBuilder name(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder name(Component component) {
        Components.setItemName(this.meta, component);
        return this;
    }

    public List<String> lore() {
        return this.meta.hasLore() ? this.meta.getLore() : new ArrayList<>();
    }

    public ItemBuilder lore(Collection<String> lore) {
        List<String> itemLore = lore();
        itemLore.addAll(lore);

        this.meta.setLore(itemLore);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        List<String> itemLore = lore();
        Collections.addAll(itemLore, lore);

        this.meta.setLore(itemLore);
        return this;
    }

    public ItemBuilder lore(Component... lore) {
        if (NMS.isInitialized()) {
            NMS.get().addItemLoreComponents(this.meta, lore);
        } else {
            List<String> itemLore = lore();
            for (Component component : lore) {
                itemLore.add(Constants.LEGACY_SERIALIZER.serialize(component));
            }
            this.meta.setLore(itemLore);
        }
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder enchantGlow(Boolean glow) {
        if (ITEM_COMPONENTS) {
            this.meta.setEnchantmentGlintOverride(glow);
        }
        return this;
    }

    public ItemBuilder attribute(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public <T, Z> ItemBuilder persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        this.meta.getPersistentDataContainer().set(key, type, value);
        return this;
    }

    public Set<ItemFlag> flags() {
        return this.meta.getItemFlags();
    }

    public ItemBuilder flags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder hideTooltip() {
        if (ITEM_COMPONENTS) {
            this.meta.setHideTooltip(true);
        } else {
            this.meta.setDisplayName(ChatColor.WHITE.toString());
        }
        return this;
    }

    public ItemBuilder fireproof() {
        if (ITEM_COMPONENTS) {
            this.meta.setFireResistant(true);
        }
        return this;
    }

    public ItemBuilder maxStackSize(Integer max) {
        if (ITEM_COMPONENTS) {
            this.meta.setMaxStackSize(max);
        }
        return this;
    }

    public ItemBuilder maxDurability(Integer max) {
        if (ITEM_COMPONENTS) {
            modify(Damageable.class, m -> m.setMaxDamage(max));
        }
        return this;
    }

    public ItemBuilder damage(int damage) {
        modify(Damageable.class, m -> m.setDamage(damage));
        return this;
    }

    public ItemBuilder edible(int nutrition, float saturation, float eatTime, boolean alwaysEdible) {
        if (ITEM_COMPONENTS) {
            FoodComponent food = this.meta.getFood();
            food.setNutrition(nutrition);
            food.setSaturation(saturation);
            food.setEatSeconds(eatTime);
            food.setCanAlwaysEat(alwaysEdible);

            this.meta.setFood(food);
        }
        return this;
    }

    public ItemBuilder foodEffect(PotionEffect effect, float probability) {
        if (ITEM_COMPONENTS && this.meta.hasFood()) {
            FoodComponent food = this.meta.getFood();
            food.addEffect(effect, probability);

            this.meta.setFood(food);
        }
        return this;
    }

    public ItemBuilder foodEffect(EffectBuilder builder, float probability) {
        return foodEffect(builder.build(), probability);
    }

    public ItemBuilder color(Color color) {
        if (this.meta instanceof ColorableArmorMeta m) {
            m.setColor(color);
        } else if (this.meta instanceof PotionMeta m) {
            m.setColor(color);
        }
        return this;
    }

    public ItemBuilder trim(TrimMaterial material, TrimPattern pattern) {
        return trim(new ArmorTrim(material, pattern));
    }

    public ItemBuilder trim(ArmorTrim trim) {
        modify(ArmorMeta.class, m -> m.setTrim(trim));
        return this;
    }

    public boolean unbreakable() {
        return this.meta.isUnbreakable();
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder skullProfile(String url) {
        return modify(SkullMeta.class, m -> m.setOwnerProfile(Utils.createProfile(url)));
    }

    public ItemBuilder skullProfile(PlayerProfile profile) {
        return modify(SkullMeta.class, m -> m.setOwnerProfile(profile));
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }

    @Override
    public ItemStack get() {
        return build();
    }
}
