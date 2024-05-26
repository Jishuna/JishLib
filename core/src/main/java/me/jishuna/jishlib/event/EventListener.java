package me.jishuna.jishlib.event;

import java.util.function.Consumer;
import org.bukkit.event.Event;

public class EventListener {
    private final EventBus bus;
    private final ListenerData priorityEvent;
    private final Consumer<Event> consumer;

    public EventListener(EventBus bus, ListenerData priorityEvent, Consumer<Event> consumer) {
        this.bus = bus;
        this.priorityEvent = priorityEvent;
        this.consumer = consumer;
    }

    protected void execute(Event event) {
        this.consumer.accept(event);
    }

    public ListenerData getEvent() {
        return this.priorityEvent;
    }

    public void discard() {
        this.bus.unsubscribe(this);
    }

    protected String getDebugString() {
        return this.consumer.toString();
    }
}
