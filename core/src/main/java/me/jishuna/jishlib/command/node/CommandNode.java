package me.jishuna.jishlib.command.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import me.jishuna.jishlib.command.CommandException;
import me.jishuna.jishlib.command.CommandInfo;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.Messages;

public abstract class CommandNode {
    private final Map<String, CommandNode> children = new HashMap<>();
    private CommandNode defaultNode;
    protected final CommandInfo info;

    protected CommandNode(CommandInfo info) {
        this.info = info;
    }

    protected final boolean checkSubcommands(CommandSender sender, ArgumentQueue arguments) {
        CommandNode node;
        if (arguments.isEmpty()) {
            node = this.defaultNode;
        } else {
            node = this.children.get(arguments.peek());
        }

        if (node == null) {
            return false;
        }

        if (!sender.hasPermission(node.info.permission())) {
            throw new CommandException(Messages.get("command.invalid-arg", Placeholder.unparsed("arg", arguments.peek())));
        }

        arguments.poll(String.class);
        node.handleCommand(sender, arguments);
        return true;
    }

    public void addChildNode(String name, CommandNode node) {
        this.children.put(name, node);
    }

    public void setDefaultNode(CommandNode node) {
        this.defaultNode = node;
    }

    public List<String> getApplicableSubcommands(CommandSender sender) {
        List<String> commands = new ArrayList<>();

        for (Entry<String, CommandNode> entry : this.children.entrySet()) {
            if (sender.hasPermission(entry.getValue().info.permission())) {
                commands.add(entry.getKey());
            }
        }

        return commands;
    }

    protected abstract void handleCommand(CommandSender sender, ArgumentQueue arguments);

    protected List<String> handleTabComplete(CommandSender sender, ArgumentQueue arguments) {
        String subCommand = arguments.peek();
        CommandNode childNode = this.children.get(subCommand);
        if (childNode == null || !sender.hasPermission(childNode.info.permission())) {
            List<String> subCommands = getApplicableSubcommands(sender);
            if (arguments.isEmpty()) {
                return subCommands;
            }

            return StringUtil.copyPartialMatches(subCommand, subCommands, new ArrayList<>());
        }

        arguments.poll(String.class);
        return childNode.handleTabComplete(sender, arguments);
    }

    public CommandInfo getCommandInfo() {
        return this.info;
    }
}
