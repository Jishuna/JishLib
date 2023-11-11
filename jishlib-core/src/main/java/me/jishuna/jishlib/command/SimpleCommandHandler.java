package me.jishuna.jishlib.command;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * A simple command executor with a required permission
 */
public abstract class SimpleCommandHandler implements CommandExecutor, TabCompleter {
    private final String permission;

    /**
     * Creates a new SimpleCommandHandler with the provided permission
     *
     * @param permission the permission required for this command.
     */
    protected SimpleCommandHandler(String permission) {
        this.permission = permission;
    }

    /**
     * Gets the required permission for this command.
     *
     * @return the required permission
     */
    public String getPermission() {
        return this.permission;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
