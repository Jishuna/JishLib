package me.jishuna.jishlib.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import me.jishuna.jishlib.util.Utils;

public class ItemBuilder implements ItemstackRepresentable {

    private ItemStack item;
    private ItemMeta meta;

    private ItemBuilder() {
    }

    public static ItemBuilder create(Material material) {
        return create(material, 1);
    }

    public static ItemBuilder create(Material material, int amount) {
        ItemBuilder builder = new ItemBuilder();
        builder.item = new ItemStack(material, amount);
        builder.meta = builder.item.getItemMeta();

        return builder;
    }

    public static ItemBuilder modifyItem(ItemStack item) {
        ItemBuilder builder = new ItemBuilder();
        builder.item = item;
        builder.meta = item.getItemMeta();

        return builder;
    }

    public static ItemBuilder modifyClone(ItemStack item) {
        return modifyItem(item.clone());
    }

    public <T extends ItemMeta, R> R get(Class<T> metaType, Function<T, R> action) {
        if (!metaType.isAssignableFrom(this.meta.getClass())) {
            return null;
        }

        return action.apply(metaType.cast(this.meta));
    }

    public <T extends ItemMeta> ItemBuilder modify(Class<T> metaType, Consumer<T> action) {
        if (!metaType.isAssignableFrom(this.meta.getClass())) {
            return this;
        }

        action.accept(metaType.cast(this.meta));
        return this;
    }

    public ItemBuilder apply(Consumer<ItemBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public ItemBuilder type(Material type) {
        this.item.setType(type);
        this.meta = Bukkit.getItemFactory().asMetaFor(this.meta, type);
        return this;
    }

    public Material type() {
        return this.item.getType();
    }

    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public int amount() {
        return this.item.getAmount();
    }

    public ItemBuilder name(String name) {
        return name(name, false);
    }

    public ItemBuilder name(String name, boolean append) {
        if (name == null) {
            return this;
        }

        if (append) {
            this.meta.setDisplayName(this.meta.getDisplayName() + name);
        } else {
            this.meta.setDisplayName(name);
        }
        return this;
    }

    public String name() {
        return this.meta.hasDisplayName() ? this.meta.getDisplayName() : null;
    }

    public ItemBuilder lore(List<String> lore) {
        if (lore == null) {
            return this;
        }

        List<String> itemLore = lore();
        itemLore.addAll(lore);

        this.meta.setLore(itemLore);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        List<String> itemLore = lore();
        List<String> loreList = Arrays.stream(lore).toList();
        itemLore.addAll(loreList);

        this.meta.setLore(itemLore);
        return this;
    }

    public ItemBuilder removeLore(int index) {
        List<String> itemLore = lore();
        itemLore.remove(index);

        this.meta.setLore(itemLore);
        return this;
    }

    public ItemBuilder clearLore() {
        this.meta.setLore(new ArrayList<>());
        return this;
    }

    public List<String> lore() {
        return this.meta.hasLore() ? this.meta.getLore() : new ArrayList<>();
    }

    public ItemBuilder damage(int damage) {
        if (this.meta instanceof Damageable damageable) {
            damageable.setDamage(damage);
        }
        return this;
    }

    public int damage() {
        if (this.meta instanceof Damageable damageable) {
            return damageable.getDamage();
        }
        return 0;
    }

    public ItemBuilder durability(int durability) {
        if (this.meta instanceof Damageable damageable) {
            damageable.setDamage(this.item.getType().getMaxDurability() - durability);
        }
        return this;
    }

    public int durability() {
        if (this.meta instanceof Damageable damageable) {
            return this.item.getType().getMaxDurability() - damageable.getDamage();
        }
        return 0;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder storedEnchantment(Enchantment enchantment, int level) {
        return modify(EnchantmentStorageMeta.class, enchantMeta -> enchantMeta.addStoredEnchant(enchantment, level, true));
    }

    public int level(Enchantment enchantment) {
        return this.meta.getEnchantLevel(enchantment);
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public boolean unbreakable() {
        return this.meta.isUnbreakable();
    }

    public ItemBuilder hideAll() {
        this.meta.addItemFlags(ItemFlag.values());
        return this;
    }

    public ItemBuilder flags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    public Set<ItemFlag> flags() {
        return this.meta.getItemFlags();
    }

    public <T, Z> ItemBuilder persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        this.meta.getPersistentDataContainer().set(key, type, value);
        return this;
    }

    public ItemBuilder removePersistentData(NamespacedKey key) {
        this.meta.getPersistentDataContainer().remove(key);
        return this;
    }

    public ItemBuilder modelData(Integer index) {
        this.meta.setCustomModelData(index);
        return this;
    }

    public Integer modelData() {
        return this.meta.hasCustomModelData() ? this.meta.getCustomModelData() : null;
    }

    public ItemBuilder modifier(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder removeModifier(Attribute attribute, AttributeModifier modifier) {
        this.meta.removeAttributeModifier(attribute, modifier);
        return this;
    }

    public Collection<AttributeModifier> modifiers(Attribute attribute) {
        if (!this.meta.hasAttributeModifiers()) {
            return Collections.emptyList();
        }

        Collection<AttributeModifier> modifiers = this.meta.getAttributeModifiers(attribute);
        return modifiers == null ? Collections.emptyList() : modifiers;
    }

    public ItemBuilder skullTexture(String texture) {
        return modify(SkullMeta.class, skullMeta -> {
            PlayerProfile profile = Utils.createProfile(texture);
            skullMeta.setOwnerProfile(profile);
        });
    }

    public ItemBuilder skullProfile(PlayerProfile profile) {
        return modify(SkullMeta.class, skullMeta -> skullMeta.setOwnerProfile(profile));
    }

    public PlayerProfile skullProfile() {
        return SkullMeta.class.isAssignableFrom(this.meta.getClass()) ? ((SkullMeta) this.meta).getOwnerProfile() : null;
    }

    public Class<? extends ItemMeta> metaClass() {
        return this.meta.getClass();
    }

    public ItemStack build() {
        ItemStack finalItem = this.item;
        finalItem.setItemMeta(this.meta);

        return finalItem;
    }

    @Override
    public ItemBuilder clone() {
        ItemBuilder clone = new ItemBuilder();
        clone.item = this.item.clone();
        clone.meta = this.meta.clone();

        return clone;
    }

    @Override
    public ItemStack asItemStack() {
        return build();
    }
}
