package me.jishuna.jishlib.command.node;

import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.CommandException;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.Messages;
import me.jishuna.jishlib.util.Components;

public class BranchNode extends CommandNode {

    public BranchNode(String permission) {
        super(permission);
    }

    @Override
    protected void handleCommand(CommandSender sender, ArgumentQueue arguments) {
        if (checkSubcommands(sender, arguments)) {
            return;
        }

        throw new CommandException(Components.toString(Messages.get("command.invalid-arg")));
    }
}
