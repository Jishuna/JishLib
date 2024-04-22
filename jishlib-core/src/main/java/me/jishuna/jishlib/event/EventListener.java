package me.jishuna.jishlib.event;

import java.util.function.Consumer;
import org.bukkit.event.Event;

public class EventListener {
    private final EventBus bus;
    private final PriorityEvent priorityEvent;
    private final Consumer<Event> consumer;

    public EventListener(EventBus bus, PriorityEvent priorityEvent, Consumer<Event> consumer) {
        this.bus = bus;
        this.priorityEvent = priorityEvent;
        this.consumer = consumer;
    }

    protected void execute(Event event) {
        this.consumer.accept(event);
    }

    public PriorityEvent getEvent() {
        return this.priorityEvent;
    }

    public void discard() {
        this.bus.unsubscribe(this);
    }
}
