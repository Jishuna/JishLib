package me.jishuna.jishlib.item;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
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
import org.bukkit.profile.PlayerTextures;

public class ItemBuilder {
    private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

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

    public static ItemBuilder modifyClone(ItemStack item) {
        return modifyItem(item.clone());
    }

    public static ItemBuilder modifyItem(ItemStack item) {
        ItemBuilder builder = new ItemBuilder();
        builder.item = item;
        builder.meta = item.getItemMeta();

        return builder;
    }

    public int amount() {
        return this.item.getAmount();
    }

    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        ItemStack finalItem = this.item;
        finalItem.setItemMeta(this.meta);

        return finalItem;
    }

    public ItemBuilder clearLore() {
        this.meta.setLore(new ArrayList<>());
        return this;
    }

    @Override
    public ItemBuilder clone() {
        ItemBuilder clone = new ItemBuilder();
        clone.item = this.item.clone();
        clone.meta = this.meta.clone();

        return clone;
    }

    public int durability() {
        if (this.meta instanceof Damageable damageable) {
            return damageable.getDamage();
        }
        return 0;
    }

    public ItemBuilder durability(int durability) {
        if (this.meta instanceof Damageable damageable) {
            damageable.setDamage(durability);
        }
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public Set<ItemFlag> flags() {
        return this.meta.getItemFlags();
    }

    public ItemBuilder flags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public int level(Enchantment enchantment) {
        return this.meta.getEnchantLevel(enchantment);
    }

    public List<String> lore() {
        return this.meta.hasLore() ? this.meta.getLore() : new ArrayList<>();
    }

    public ItemBuilder lore(List<String> lore) {
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

    public Integer modelData() {
        return this.meta.hasCustomModelData() ? this.meta.getCustomModelData() : null;
    }

    public ItemBuilder modelData(Integer value) {
        this.meta.setCustomModelData(value);
        return this;
    }

    public ItemBuilder modifier(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public Collection<AttributeModifier> modifiers(Attribute attribute) {
        if (!this.meta.hasAttributeModifiers()) {
            return Collections.emptyList();
        }

        Collection<AttributeModifier> modifiers = this.meta.getAttributeModifiers(attribute);
        return modifiers == null ? Collections.emptyList() : modifiers;
    }

    public <T extends ItemMeta> ItemBuilder modify(Class<T> metaType, Consumer<T> action) {
        if (!metaType.isAssignableFrom(this.meta.getClass())) {
            return this;
        }

        action.accept(metaType.cast(this.meta));
        return this;
    }

    public String name() {
        return this.meta.getDisplayName();
    }

    public ItemBuilder name(String name) {
        return name(name, false);
    }

    public ItemBuilder name(String name, boolean append) {
        if (append) {
            this.meta.setDisplayName(this.meta.getDisplayName() + name);
        } else {
            this.meta.setDisplayName(name);
        }
        return this;
    }

    public <T, Z> ItemBuilder persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        this.meta.getPersistentDataContainer().set(key, type, value);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder removeLore(int index) {
        List<String> itemLore = lore();
        itemLore.remove(index);

        this.meta.setLore(itemLore);
        return this;
    }

    public ItemBuilder removeModifier(Attribute attribute, AttributeModifier modifier) {
        this.meta.removeAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder removePersistentData(NamespacedKey key) {
        this.meta.getPersistentDataContainer().remove(key);
        return this;
    }

    public ItemBuilder skullProfile(PlayerProfile profile) {
        return modify(SkullMeta.class, skullMeta -> skullMeta.setOwnerProfile(profile));
    }

    public ItemBuilder skullTexture(String texture) {
        return modify(SkullMeta.class, skullMeta -> {
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.nameUUIDFromBytes(texture.getBytes()));
            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(new URL(TEXTURE_URL + texture));
            } catch (MalformedURLException ignored) {
                return;
            }

            profile.setTextures(textures);
            skullMeta.setOwnerProfile(profile);
        });
    }

    public ItemBuilder storedEnchantment(Enchantment enchantment, int level) {
        return modify(EnchantmentStorageMeta.class, storageMeta -> storageMeta.addStoredEnchant(enchantment, level, true));
    }

    public Material type() {
        return this.item.getType();
    }

    public ItemBuilder type(Material type) {
        this.item.setType(type);
        this.meta = Bukkit.getItemFactory().asMetaFor(this.meta, type);
        return this;
    }

    public boolean unbreakable() {
        return this.meta.isUnbreakable();
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }
}
