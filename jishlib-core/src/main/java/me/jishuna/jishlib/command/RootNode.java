package me.jishuna.jishlib.command;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import me.jishuna.jishlib.command.argument.ArgumentQueue;

public class RootNode extends BranchNode implements TabExecutor {

    public RootNode(String permission) {
        super(permission);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ArgumentQueue queue = new ArgumentQueue(args);

        try {
            handleCommand(sender, queue);
        } catch (CommandException e) {
            sender.sendMessage(e.getMessage());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArgumentQueue queue = new ArgumentQueue(args);

        return handleTabComplete(sender, queue);
    }
}
