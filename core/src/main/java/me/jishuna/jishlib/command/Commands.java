package me.jishuna.jishlib.command;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import me.jishuna.jishlib.Plugin;
import me.jishuna.jishlib.command.node.RootNode;
import me.jishuna.jishlib.util.ReflectionHelper;

public class Commands {
    private static final MethodHandle CONSTRUCTOR = ReflectionHelper.getConstructor(PluginCommand.class, String.class, org.bukkit.plugin.Plugin.class);
    private static final CommandMap COMMAND_MAP;

    static {
        Field field = ReflectionHelper.getField(Bukkit.getPluginManager().getClass(), "commandMap");
        COMMAND_MAP = (CommandMap) ReflectionHelper.readField(field, Bukkit.getPluginManager());
    }

    public static void register(RootNode root) {
        try {
            CommandInfo info = root.getCommandInfo();
            PluginCommand command = (PluginCommand) CONSTRUCTOR.invoke(info.name(), Plugin.getInstance());
            command.setName(info.name());
            command.setPermission(info.permission());
            command.setAliases(info.aliases());
            command.setUsage("/" + info.name());
            command.setExecutor(root);
            command.setTabCompleter(root);

            COMMAND_MAP.register(Plugin.getInstance().getName(), command);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
