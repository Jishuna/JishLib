package me.jishuna.jishlib.entity;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Parrot;

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
        if (entity instanceof Horse en) {
            return new HorseIdentifier(en);
        }
        if (entity instanceof MushroomCow en) {
            return new MooshroomIdentifier(en);
        }
        if (entity instanceof Parrot en) {
            return new ParrotIdentifier(en);
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

        for (Horse.Color color : Horse.Color.values()) {
            for (Horse.Style style : Horse.Style.values()) {
                register(new HorseIdentifier(color, style));
            }
        }

        for (MushroomCow.Variant variant : MushroomCow.Variant.values()) {
            register(new MooshroomIdentifier(variant));
        }

        for (Parrot.Variant variant : Parrot.Variant.values()) {
            register(new ParrotIdentifier(variant));
        }
    }
}
