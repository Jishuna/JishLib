package me.jishuna.jishlib.entity;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.DyeColor;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.material.Colorable;

public class EntityIdentifiers {
    private static final Map<String, EntityIdentifier> BY_STRING = new HashMap<>();

    static {
        registerExtras();

        for (EntityType type : EntityType.values()) {
            if (type == EntityType.UNKNOWN) {
                continue;
            }

            register(new EntityIdentifier(type));
        }
    }

    public static EntityIdentifier getIdentifier(Entity entity) {
        if (entity instanceof Axolotl en) {
            return new AxolotlIdentifier(en);
        }
        if (entity instanceof Cat en) {
            return new CatIdentifier(en);
        }
        if (entity instanceof Colorable en) {
            return new ColoredEntityIdentifier(en);
        }
        if (entity instanceof Fox en) {
            return new FoxIdentifier(en);
        }
        if (entity instanceof Frog en) {
            return new FrogIdentifier(en);
        }
        if (entity instanceof Horse en) {
            return new HorseIdentifier(en);
        }
        if (entity instanceof Llama en) {
            return new LlamaIdentifier(en);
        }
        if (entity instanceof MushroomCow en) {
            return new MooshroomIdentifier(en);
        }
        if (entity instanceof Panda en) {
            return new PandaIdentifier(en);
        }
        if (entity instanceof Parrot en) {
            return new ParrotIdentifier(en);
        }
        if (entity instanceof Rabbit en) {
            return new RabbitIdentifier(en);
        }
        if (entity instanceof Villager en) {
            return new VillagerIdentifier(en);
        }
        if (entity instanceof ZombieVillager en) {
            return new VillagerIdentifier(en);
        }

        return new EntityIdentifier(entity);
    }

    public static EntityIdentifier fromString(String string) {
        return BY_STRING.get(string);
    }

    private static EntityIdentifier register(EntityIdentifier identifier) {
        BY_STRING.put(identifier.toString(), identifier);
        return identifier;
    }

    private static void registerExtras() {
        for (Axolotl.Variant variant : Axolotl.Variant.values()) {
            register(new AxolotlIdentifier(variant));
        }

        for (Cat.Type type : Cat.Type.values()) {
            register(new CatIdentifier(type));
        }

        register(new ColoredEntityIdentifier(EntityType.SHEEP, null));
        register(new ColoredEntityIdentifier(EntityType.SHULKER, null));
        for (DyeColor color : DyeColor.values()) {
            register(new ColoredEntityIdentifier(EntityType.SHEEP, color));
            register(new ColoredEntityIdentifier(EntityType.SHULKER, color));
        }

        for (Fox.Type type : Fox.Type.values()) {
            register(new FoxIdentifier(type));
        }

        for (Frog.Variant variant : Frog.Variant.values()) {
            register(new FrogIdentifier(variant));
        }

        for (Horse.Color color : Horse.Color.values()) {
            for (Horse.Style style : Horse.Style.values()) {
                register(new HorseIdentifier(color, style));
            }
        }

        for (Llama.Color color : Llama.Color.values()) {
            register(new LlamaIdentifier(EntityType.LLAMA, color));
            register(new LlamaIdentifier(EntityType.TRADER_LLAMA, color));
        }

        for (MushroomCow.Variant variant : MushroomCow.Variant.values()) {
            register(new MooshroomIdentifier(variant));
        }

        for (Panda.Gene gene : Panda.Gene.values()) {
            register(new PandaIdentifier(gene));
        }

        for (Parrot.Variant variant : Parrot.Variant.values()) {
            register(new ParrotIdentifier(variant));
        }

        for (Rabbit.Type type : Rabbit.Type.values()) {
            register(new RabbitIdentifier(type));
        }

        for (Villager.Profession profession : Villager.Profession.values()) {
            for (Villager.Type type : Villager.Type.values()) {
                register(new VillagerIdentifier(EntityType.VILLAGER, profession, type));
                register(new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, type));
            }
        }
    }
}
