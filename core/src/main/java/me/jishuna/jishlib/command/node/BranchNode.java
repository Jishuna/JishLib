package me.jishuna.jishlib.command.node;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.CommandException;
import me.jishuna.jishlib.command.CommandInfo;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.Messages;

public class BranchNode extends CommandNode {

    public BranchNode(CommandInfo info) {
        super(info);
    }

    @Override
    protected void handleCommand(CommandSender sender, ArgumentQueue arguments) {
        if (checkSubcommands(sender, arguments)) {
            return;
        }

        throw new CommandException(Messages
                .get("command.invalid-arg",
                        Placeholder.unparsed("input", arguments.peek()),
                        Placeholder.unparsed("args", String.join(", ", getApplicableSubcommands(sender)))));
    }
}
