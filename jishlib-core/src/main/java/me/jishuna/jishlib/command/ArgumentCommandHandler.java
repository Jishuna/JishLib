package me.jishuna.jishlib.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

/**
 * A command executor with a number of defined arguments.
 */
public class ArgumentCommandHandler extends SimpleCommandHandler {
    private final Map<String, SimpleCommandHandler> subcommands = new HashMap<>();
    private final Supplier<String> permMessage;
    private final Supplier<String> usageMessage;

    private CommandExecutor defaultExecutor;

    /**
     * Creates a new ArgumentCommandHandler with the given properties.
     *
     * @param permission   the permission required to use this command
     * @param permMessage  a supplier for the message to send when a player does not
     *                     have permission for this command
     * @param usageMessage a supplier for the message to send when a player runs
     *                     this command with an invalid argument
     */
    public ArgumentCommandHandler(String permission, Supplier<String> permMessage, Supplier<String> usageMessage) {
        super(permission);
        this.permMessage = permMessage;
        this.usageMessage = usageMessage;
    }

    /**
     * A supplier for the message to send when a player does not have permission for
     * this command.
     *
     * @return the permission message supplier
     */
    public Supplier<String> getPermMessage() {
        return this.permMessage;
    }

    /**
     * A supplier for the message to send when a player runs this command with an
     * invalid argument.
     *
     * @return the usage message supplier
     */
    public Supplier<String> getUsageMessage() {
        return this.usageMessage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            sender.sendMessage(this.permMessage.get());
            return true;
        }

        if (args.length == 0) {
            if (this.defaultExecutor != null) {
                return this.defaultExecutor.onCommand(sender, command, alias, args);
            }
            sendUsage(sender, "none", this.subcommands.keySet());
            return true;
        }
        if (args.length > 0) {
            SimpleCommandHandler executor = this.subcommands.get(args[0]);

            if (executor == null) {
                sendUsage(sender, args[0], this.subcommands.keySet());
                return true;
            }

            return executor.onCommand(sender, command, alias, Arrays.copyOfRange(args, 1, args.length));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> validSubcommands = new ArrayList<>();

            for (Entry<String, SimpleCommandHandler> entry : this.subcommands.entrySet()) {
                if (sender.hasPermission(entry.getValue().getPermission())) {
                    validSubcommands.add(entry.getKey());
                }
            }

            return StringUtil.copyPartialMatches(args[0], validSubcommands, new ArrayList<>());
        }
        if (args.length > 1) {
            SimpleCommandHandler executor = this.subcommands.get(args[0]);
            if (executor != null) {
                return executor.onTabComplete(sender, command, alias, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return Collections.emptyList();
    }

    /**
     * Sends the usage message to the given sender using the provided argument and
     * collection of allowed arguments.
     *
     * @param sender      the sender to send to
     * @param arg         the argument that was used
     * @param allowedargs a collection of valud arguments
     */
    public void sendUsage(CommandSender sender, String arg, Collection<String> allowedargs) {
        sender.sendMessage(MessageFormat.format(this.usageMessage.get(), arg, String.join(", ", allowedargs)));
    }

    protected void addArgumentExecutor(String arg, SimpleCommandHandler exeuctor) {
        this.subcommands.put(arg, exeuctor);
    }

    protected void setDefault(SimpleCommandHandler exeuctor) {
        this.defaultExecutor = exeuctor;
    }
}
