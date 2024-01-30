package me.jishuna.jishlib.command;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.argument.ArgumentQueue;

public abstract class LeafNode extends CommandNode {

    protected LeafNode(String permission) {
        super(permission);
    }

    @Override
    protected abstract void handleCommand(CommandSender sender, ArgumentQueue arguments);

    @Override
    protected List<String> handleTabComplete(CommandSender sender, ArgumentQueue arguments) {
        return Collections.emptyList();
    }
}
