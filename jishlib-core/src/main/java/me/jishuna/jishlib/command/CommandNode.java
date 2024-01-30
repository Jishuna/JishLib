package me.jishuna.jishlib.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.MessageAPI;

public abstract class CommandNode {
    private final Map<String, CommandNode> children = new HashMap<>();
    protected final String permission;

    protected CommandNode(String permission) {
        this.permission = permission;
    }

    protected final boolean checkSubcommands(CommandSender sender, ArgumentQueue arguments) {
        String subCommand = arguments.peek();
        CommandNode childNode = this.children.get(subCommand);
        if (childNode == null) {
            return false;
        }

        if (!sender.hasPermission(childNode.permission)) {
            throw new CommandException(MessageAPI.get("command.no-permission"));
        }

        arguments.poll();
        childNode.handleCommand(sender, arguments);
        return true;
    }

    protected void addChildNode(String name, CommandNode node) {
        this.children.put(name, node);
    }

    protected List<String> getApplicableSubcommands(CommandSender sender) {
        List<String> commands = new ArrayList<>();

        for (Entry<String, CommandNode> entry : this.children.entrySet()) {
            if (sender.hasPermission(entry.getValue().permission)) {
                commands.add(entry.getKey());
            }
        }

        return commands;
    }

    protected abstract void handleCommand(CommandSender sender, ArgumentQueue arguments);

    protected List<String> handleTabComplete(CommandSender sender, ArgumentQueue arguments) {
        String subCommand = arguments.peek();
        CommandNode childNode = this.children.get(subCommand);
        if (childNode == null || !sender.hasPermission(childNode.permission)) {
            List<String> subCommands = getApplicableSubcommands(sender);
            if (arguments.isEmpty()) {
                return subCommands;
            }

            return StringUtil.copyPartialMatches(subCommand, subCommands, new ArrayList<>());
        }

        arguments.poll();
        return childNode.handleTabComplete(sender, arguments);
    }
}
