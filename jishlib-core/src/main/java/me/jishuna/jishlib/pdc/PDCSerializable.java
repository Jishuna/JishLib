package me.jishuna.jishlib.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

public interface PDCSerializable {
    public PersistentDataContainer toContainer(PersistentDataAdapterContext context);
}
