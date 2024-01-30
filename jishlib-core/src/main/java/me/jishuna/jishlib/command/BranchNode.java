package me.jishuna.jishlib.command;

import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.MessageAPI;

public class BranchNode extends CommandNode {

    public BranchNode(String permission) {
        super(permission);
    }

    @Override
    protected void handleCommand(CommandSender sender, ArgumentQueue arguments) {
        if (checkSubcommands(sender, arguments)) {
            return;
        }

        throw new CommandException(MessageAPI.getLegacy("command.invalid-arg", arguments.poll(), String.join(", ", getApplicableSubcommands(sender))));
    }
}
