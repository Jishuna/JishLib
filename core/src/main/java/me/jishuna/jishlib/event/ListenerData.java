package me.jishuna.jishlib.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public record ListenerData(Class<? extends Event> eventClass, EventPriority priority, boolean ignoreCancelled) {

    public String getDebugString() {
        return this.eventClass.getName() + "[priority=" + this.priority + ", ignoreCancelled=" + this.ignoreCancelled + "]";
    }
}
