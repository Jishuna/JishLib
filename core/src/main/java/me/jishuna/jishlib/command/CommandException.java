package me.jishuna.jishlib.command;

import net.kyori.adventure.text.Component;

public class CommandException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final Component component;

    public CommandException(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
