package me.jishuna.jishlib.command;

import java.util.Map;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.MessageSystem;

public class BranchNode extends CommandNode {

    public BranchNode(String permission) {
        super(permission);
    }

    @Override
    protected void handleCommand(CommandSender sender, ArgumentQueue arguments) {
        if (checkSubcommands(sender, arguments)) {
            return;
        }

        throw new CommandException(MessageSystem
                .get("command.invalid-arg",
                        Map.of("input", arguments::poll, "args", () -> String.join(", ", getApplicableSubcommands(sender)))));
    }
}
