package me.jishuna.jishlib.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public record PriorityEvent(Class<? extends Event> eventClass, EventPriority priority) {
}
