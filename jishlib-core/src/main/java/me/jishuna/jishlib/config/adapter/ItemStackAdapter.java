package me.jishuna.jishlib.config.adapter;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.jishlib.util.Utils;

public class ItemStackAdapter implements TypeAdapter<ConfigurationSection, ItemStack> {
    private static final TypeAdapterString<String, Material> MATERIAL_ADAPTER = ConfigAPI.getStringAdapter(Material.class);

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<ItemStack> getRuntimeType() {
        return ItemStack.class;
    }

    @Override
    public ItemStack read(ConfigurationSection value) {
        Material material = MATERIAL_ADAPTER.fromString(value.getString("type"));
        if (material == null) {
            return null;
        }

        ItemBuilder builder = ItemBuilder
                .create(material)
                .amount(value.getInt("amount", 1))
                .name(StringUtils.miniMessageToLegacy(value.getString("name")))
                .lore(value.getStringList("lore").stream().map(StringUtils::miniMessageToLegacy).toList());

        if (SkullMeta.class.isAssignableFrom(builder.metaClass())) {
            readSkull(builder, value);
        }

        return builder.build();
    }

    @Override
    public ConfigurationSection write(ItemStack value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        ItemBuilder builder = ItemBuilder.modifyItem(value);

        ConfigAPI.setIfAbsent(existing, "type", () -> MATERIAL_ADAPTER.toString(value.getType()));
        ConfigAPI.setIfAbsent(existing, "amount", value::getAmount);
        ConfigAPI.setIfAbsent(existing, "name", () -> StringUtils.legacyToMiniMessage(builder.name()));
        ConfigAPI.setIfAbsent(existing, "lore", () -> builder.lore().stream().map(StringUtils::legacyToMiniMessage).toList());

        if (SkullMeta.class.isAssignableFrom(builder.metaClass())) {
            writeSkull(builder, existing, replace);
        }

        return existing;
    }

    private void readSkull(ItemBuilder builder, ConfigurationSection value) {
        if (value.isSet("skull-texture")) {
            builder.modify(SkullMeta.class, meta -> meta.setOwnerProfile(Utils.createProfile(value.getString("skull-texture"))));
        }
    }

    private void writeSkull(ItemBuilder builder, ConfigurationSection existing, boolean replace) {
        PlayerProfile skullProfile = builder.skullProfile();
        if (skullProfile != null && skullProfile.getTextures().getSkin() != null) {
            String texture = skullProfile.getTextures().getSkin().toString();
            ConfigAPI.setIfAbsent(existing, "skull-texture", () -> texture.substring(texture.lastIndexOf('/') + 1));
        }
    }
}
