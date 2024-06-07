package me.jishuna.jishlib.command.node;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import me.jishuna.jishlib.command.CommandException;
import me.jishuna.jishlib.command.CommandInfo;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.util.Components;

public class RootNode extends BranchNode implements TabExecutor {

    public RootNode(CommandInfo info) {
        super(info);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ArgumentQueue queue = new ArgumentQueue(args);

        try {
            handleCommand(sender, queue);
        } catch (CommandException e) {
            Components.sendMessage(sender, e.getComponent());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArgumentQueue queue = new ArgumentQueue(args);

        return handleTabComplete(sender, queue);
    }
}
