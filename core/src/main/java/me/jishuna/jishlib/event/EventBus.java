package me.jishuna.jishlib.event;

import java.util.ArrayList;
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
    private final Map<ListenerData, List<EventListener>> handlers = new ConcurrentHashMap<>();
    private final Set<ListenerData> subscribed = ConcurrentHashMap.newKeySet();

    private final Plugin plugin;
    private final EventPriority defaultPriority;

    public EventBus(Plugin plugin, EventPriority defaultPriority) {
        this.plugin = plugin;
        this.defaultPriority = defaultPriority;
    }

    public <T extends Event> EventListener subscribe(Class<T> eventClass, Consumer<T> action) {
        return subscribe(eventClass, this.defaultPriority, true, action);
    }

    public <T extends Event> EventListener subscribe(Class<T> eventClass, EventPriority priority, Consumer<T> action) {
        return subscribe(eventClass, priority, true, action);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> EventListener subscribe(Class<T> eventClass, EventPriority priority, boolean ignoreCancelled, Consumer<T> action) {
        ListenerData priorityEvent = new ListenerData(eventClass, priority, ignoreCancelled);
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

    public void discard() {
        this.handlers.clear();
        this.subscribed.clear();
        HandlerList.unregisterAll(this);
    }

    public List<String> getDebugData() {
        List<String> list = new ArrayList<>();

        this.handlers.forEach((k, v) -> {
            list.add("  " + k.getDebugString());
            v.forEach(l -> list.add("   - " + l.getDebugString()));
        });

        return list;
    }

    private void subscribeEvent(ListenerData data) {
        if (this.subscribed.contains(data)) {
            return;
        }

        Bukkit.getPluginManager().registerEvent(data.eventClass(), this, data.priority(), (ignored, event) -> {
            List<EventListener> listeners = this.handlers.get(data);
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
        }, this.plugin, data.ignoreCancelled());
    }
}
