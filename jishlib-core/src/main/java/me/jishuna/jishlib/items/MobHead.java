package me.jishuna.jishlib.items;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MobHead {
    private static final Map<NamespacedKey, ItemStack> TYPE_MAP = new HashMap<>();

    public static final ItemStack ZOMBIE = ItemBuilder.create(Material.ZOMBIE_HEAD).build();
    public static final ItemStack SKELETON = ItemBuilder.create(Material.SKELETON_SKULL).build();
    public static final ItemStack CREEPER = ItemBuilder.create(Material.CREEPER_HEAD).build();
    public static final ItemStack WITHER_SKELETON = ItemBuilder.create(Material.WITHER_SKELETON_SKULL).build();
    public static final ItemStack ENDER_DRAGON = ItemBuilder.create(Material.DRAGON_HEAD).build();

    public static final ItemStack WOLF = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("d0498de6f5b09e0ce35a7292fe50b79fce9065d9be8e2a87c7a13566efb26d72").build();
    public static final ItemStack IRON_GOLEM = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("e13f34227283796bc017244cb46557d64bd562fa9dab0e12af5d23ad699cf697").build();
    public static final ItemStack BEE = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("4420c9c43e095880dcd2e281c81f47b163b478f58a584bb61f93e6e10a155f31").build();
    public static final ItemStack DOLPHIN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3").build();
    public static final ItemStack LLAMA = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("9f7d90b305aa64313c8d4404d8d652a96eba8a754b67f4347dcccdd5a6a63398").build();
    public static final ItemStack TRADER_LLAMA = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("56307f42fc88ebc211e04ea2bb4d247b7428b711df9a4e0c6d1b921589e443a1").build();
    public static final ItemStack PANDA = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("dca096eea506301bea6d4b17ee1605625a6f5082c71f74a639cc940439f47166").build();
    public static final ItemStack GOAT = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("a662336d8ae092407e58f7cc80d20f20e7650357a454ce16e3307619a0110648").build();
    public static final ItemStack POLAR_BEAR = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("c4fe926922fbb406f343b34a10bb98992cee4410137d3f88099427b22de3ab90").build();
    
    public static final ItemStack ENDERMAN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf").build();
    public static final ItemStack ENDERMITE = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("5bc7b9d36fb92b6bf292be73d32c6c5b0ecc25b44323a541fae1f1e67e393a3e").build();
    public static final ItemStack SPIDER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("35e248da2e108f09813a6b848a0fcef111300978180eda41d3d1a7a8e4dba3c3").build();
    public static final ItemStack CAVE_SPIDER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("eccc4a32d45d74e8b14ef1ffd55cd5f381a06d4999081d52eaea12e13293e209").build();
    public static final ItemStack ZOMBIE_VILLAGER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("fb552c90f212e855d12255d5cd62ed38b9cd7e30e73f0ea779d1764330e69264").build();
    public static final ItemStack HUSK = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("d48a5a5e4df90528dba35e0667cdc0a7ddc025740a2b19bf355a68ab899a2fe7").build();
    public static final ItemStack STRAY = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("2c5097916bc0565d30601c0eebfeb287277a34e867b4ea43c63819d53e89ede7").build();
    public static final ItemStack WITCH = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("7e71a6eb303ab7e6f70ed54df9146a80eadf396417cee9495773ffbebfad887c").build();
    public static final ItemStack SLIME = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("61affd31efc37ba84f50187394d8688344ccd06cdc926ddfcf2df116986dca9").build();
    public static final ItemStack SILVERFISH = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540").build();
    public static final ItemStack DROWNED = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("c84df79c49104b198cdad6d99fd0d0bcf1531c92d4ab6269e40b7d3cbbb8e98c").build();
    public static final ItemStack GUARDIAN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("a0bf34a71e7715b6ba52d5dd1bae5cb85f773dc9b0d457b4bfc5f9dd3cc7c94").build();
    public static final ItemStack ELDER_GUARDIAN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("e92089618435a0ef63e95ee95a92b83073f8c33fa77dc5365199bad33b6256").build();
    public static final ItemStack PILLAGER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("4aee6bb37cbfc92b0d86db5ada4790c64ff4468d68b84942fde04405e8ef5333").build();
    public static final ItemStack RAVAGER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("5c73e16fa2926899cf18434360e2144f84ef1eb981f996148912148dd87e0b2a").build();
    public static final ItemStack EVOKER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("e79f133a85fe00d3cf252a04d6f2eb2521fe299c08e0d8b7edbf962740a23909").build();
    public static final ItemStack VINDICATOR = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("e79f133a85fe00d3cf252a04d6f2eb2521fe299c08e0d8b7edbf962740a23909").build();
    public static final ItemStack VEX = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("869a7c6f0a7358c594c29d3d42cf7b69638ef3b5b3ec1f9d38150c8b2bff7813").build();
    public static final ItemStack ILLUSIONER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("4639d325f4494258a473a93a3b47f34a0c51b3fceaf59fee87205a5e7ff31f68").build();
    public static final ItemStack PHANTOM = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("7e95153ec23284b283f00d19d29756f244313a061b70ac03b97d236ee57bd982").build();
    public static final ItemStack WARDEN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("bc9c84349742164a22971ee54516fff91d868da72cdcce62069db128c42154b2").build();
    public static final ItemStack PIGLIN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("d71b3aee182b9a99ed26cbf5ecb47ae90c2c3adc0927dde102c7b30fdf7f4545").build();
    public static final ItemStack PIGLIN_BRUTE = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf").build();
    public static final ItemStack HOGLIN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75").build();
    public static final ItemStack BLAZE = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("b20657e24b56e1b2f8fc219da1de788c0c24f36388b1a409d0cd2d8dba44aa3b").build();
    public static final ItemStack MAGMA_CUBE = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("a1c97a06efde04d00287bf20416404ab2103e10f08623087e1b0c1264a1c0f0c").build();
    public static final ItemStack GHAST = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("8b6a72138d69fbbd2fea3fa251cabd87152e4f1c97e5f986bf685571db3cc0").build();
    public static final ItemStack ZOMBIFIED_PIGLIN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("e935842af769380f78e8b8a88d1ea6ca2807c1e5693c2cf797456620833e936f").build();
    public static final ItemStack ZOGLIN = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("c19b7b5e9ffd4e22b890ab778b4795b662faff2b4978bf815574e48b0e52b301").build();
    public static final ItemStack WITHER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("ee280cefe946911ea90e87ded1b3e18330c63a23af5129dfcfe9a8e166588041").build();
    public static final ItemStack SHULKER = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("537a294f6b7b4ba437e5cb35fb20f46792e7ac0a490a66132a557124ec5f997a").build();

    static {
        TYPE_MAP.put(EntityType.ZOMBIE.getKey(), ZOMBIE);
        TYPE_MAP.put(EntityType.SKELETON.getKey(), SKELETON);
        TYPE_MAP.put(EntityType.CREEPER.getKey(), CREEPER);
        TYPE_MAP.put(EntityType.WITHER_SKELETON.getKey(), WITHER_SKELETON);
        TYPE_MAP.put(EntityType.ENDER_DRAGON.getKey(), ENDER_DRAGON);

        TYPE_MAP.put(EntityType.GIANT.getKey(), ZOMBIE);
        
        TYPE_MAP.put(EntityType.WOLF.getKey(), WOLF);
        TYPE_MAP.put(EntityType.IRON_GOLEM.getKey(), IRON_GOLEM);
        TYPE_MAP.put(EntityType.BEE.getKey(), BEE);
        TYPE_MAP.put(EntityType.DOLPHIN.getKey(), DOLPHIN);
        TYPE_MAP.put(EntityType.LLAMA.getKey(), LLAMA);
        TYPE_MAP.put(EntityType.TRADER_LLAMA.getKey(), TRADER_LLAMA);
        TYPE_MAP.put(EntityType.PANDA.getKey(), PANDA);
        TYPE_MAP.put(EntityType.GOAT.getKey(), GOAT);
        TYPE_MAP.put(EntityType.POLAR_BEAR.getKey(), POLAR_BEAR);

        TYPE_MAP.put(EntityType.ENDERMAN.getKey(), ENDERMAN);
        TYPE_MAP.put(EntityType.ENDERMITE.getKey(), ENDERMITE);
        TYPE_MAP.put(EntityType.SPIDER.getKey(), SPIDER);
        TYPE_MAP.put(EntityType.CAVE_SPIDER.getKey(), CAVE_SPIDER);
        TYPE_MAP.put(EntityType.ZOMBIE_VILLAGER.getKey(), ZOMBIE_VILLAGER);
        TYPE_MAP.put(EntityType.HUSK.getKey(), HUSK);
        TYPE_MAP.put(EntityType.STRAY.getKey(), STRAY);
        TYPE_MAP.put(EntityType.WITCH.getKey(), WITCH);
        TYPE_MAP.put(EntityType.SLIME.getKey(), SLIME);
        TYPE_MAP.put(EntityType.SILVERFISH.getKey(), SILVERFISH);
        TYPE_MAP.put(EntityType.DROWNED.getKey(), DROWNED);
        TYPE_MAP.put(EntityType.GUARDIAN.getKey(), GUARDIAN);
        TYPE_MAP.put(EntityType.ELDER_GUARDIAN.getKey(), ELDER_GUARDIAN);
        TYPE_MAP.put(EntityType.PILLAGER.getKey(), PILLAGER);
        TYPE_MAP.put(EntityType.RAVAGER.getKey(), RAVAGER);
        TYPE_MAP.put(EntityType.EVOKER.getKey(), EVOKER);
        TYPE_MAP.put(EntityType.VINDICATOR.getKey(), VINDICATOR);
        TYPE_MAP.put(EntityType.VEX.getKey(), VEX);
        TYPE_MAP.put(EntityType.ILLUSIONER.getKey(), ILLUSIONER);
        TYPE_MAP.put(EntityType.PHANTOM.getKey(), PHANTOM);
        TYPE_MAP.put(NamespacedKey.minecraft("warden"), WARDEN);
        TYPE_MAP.put(EntityType.PIGLIN.getKey(), PIGLIN);
        TYPE_MAP.put(EntityType.PIGLIN_BRUTE.getKey(), PIGLIN_BRUTE);
        TYPE_MAP.put(EntityType.HOGLIN.getKey(), HOGLIN);
        TYPE_MAP.put(EntityType.BLAZE.getKey(), BLAZE);
        TYPE_MAP.put(EntityType.MAGMA_CUBE.getKey(), MAGMA_CUBE);
        TYPE_MAP.put(EntityType.GHAST.getKey(), GHAST);
        TYPE_MAP.put(EntityType.ZOMBIFIED_PIGLIN.getKey(), ZOMBIFIED_PIGLIN);
        TYPE_MAP.put(EntityType.ZOGLIN.getKey(), ZOGLIN);
        TYPE_MAP.put(EntityType.WITHER.getKey(), WITHER);
        TYPE_MAP.put(EntityType.SHULKER.getKey(), SHULKER);
    }

    public static ItemStack forType(EntityType type) {
        if (type == null || !type.isAlive()) {
            return null;
        }
        return TYPE_MAP.get(type.getKey());
    }
}
