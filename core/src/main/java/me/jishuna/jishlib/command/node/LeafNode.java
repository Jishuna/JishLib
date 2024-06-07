package me.jishuna.jishlib.command.node;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.CommandInfo;
import me.jishuna.jishlib.command.argument.ArgumentQueue;

public abstract class LeafNode extends CommandNode {

    protected LeafNode(CommandInfo info) {
        super(info);
    }

    @Override
    protected List<String> handleTabComplete(CommandSender sender, ArgumentQueue arguments) {
        return Collections.emptyList();
    }
}
