package me.jishuna.jishlib.pdc;

import me.jishuna.jishlib.util.Key;
import org.bukkit.persistence.PersistentDataType;

public final class PDCTypes {

    public static final PersistentDataType<String, Key> KEY = new KeyPersistentDataType();
    public static final PersistentDataType<long[], java.util.UUID> UUID = new UUIDPersistentDataType();

    private PDCTypes() {
    }
}
