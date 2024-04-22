package me.jishuna.jishlib.event;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EventBus implements Listener {
    private final Map<PriorityEvent, List<EventListener>> handlers = new ConcurrentHashMap<>();
    private final Set<PriorityEvent> subscribed = ConcurrentHashMap.newKeySet();

    private final Plugin plugin;
    private final EventPriority defaultPriority;

    public EventBus(Plugin plugin, EventPriority defaultPriority) {
        this.plugin = plugin;
        this.defaultPriority = defaultPriority;
    }

    public <T extends Event> EventListener subscribe(Class<T> eventClass, Consumer<T> action) {
        return subscribe(eventClass, action, this.defaultPriority);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> EventListener subscribe(Class<T> eventClass, Consumer<T> action, EventPriority priority) {
        PriorityEvent priorityEvent = new PriorityEvent(eventClass, priority);
        EventListener listener = new EventListener(this, priorityEvent, (Consumer<Event>) action);
        this.handlers.computeIfAbsent(priorityEvent, k -> new LinkedList<>()).add(listener);

        subscribeEvent(priorityEvent);
        return listener;
    }

    public void unsubscribe(EventListener listener) {
        List<EventListener> listeners = this.handlers.get(listener.getEvent());
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    private void subscribeEvent(PriorityEvent priorityEvent) {
        if (this.subscribed.contains(priorityEvent)) {
            return;
        }

        Bukkit.getPluginManager().registerEvent(priorityEvent.eventClass(), this, priorityEvent.priority(), (ignored, event) -> {
            List<EventListener> listeners = this.handlers.get(priorityEvent);
            for (EventListener listener : listeners) {
                if (!listener.getEvent().eventClass().isInstance(event)) {
                    continue;
                }

                try {
                    listener.execute(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, this.plugin, true);
    }

    public void discard() {
        this.handlers.clear();
        this.subscribed.clear();
        HandlerList.unregisterAll(this);
    }
}
