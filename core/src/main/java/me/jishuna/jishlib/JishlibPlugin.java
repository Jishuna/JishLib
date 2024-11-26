package me.jishuna.jishlib;

import me.jishuna.jishlib.event.JishlibDisableEvent;
import me.jishuna.jishlib.event.JishlibReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class JishlibPlugin extends JavaPlugin {
    private static JishlibPlugin instance;

    public static JishlibPlugin instance() {
        return instance;
    }

    public void loaded() {

    }

    public void enabled() {

    }

    public void reloaded() {

    }

    public void disabled() {

    }

    @Override
    public final void onLoad() {
        instance = this;
        loaded();
    }

    @Override
    public final void onEnable() {
        enabled();
    }

    @Override
    public final void reloadConfig() {
        reloaded();

        JishlibReloadEvent event = new JishlibReloadEvent();
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public final void onDisable() {
        disabled();
        instance = null;

        JishlibDisableEvent event = new JishlibDisableEvent();
        Bukkit.getPluginManager().callEvent(event);
    }
}
