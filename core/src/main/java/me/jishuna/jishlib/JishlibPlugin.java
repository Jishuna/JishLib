package me.jishuna.jishlib;

import org.bukkit.plugin.java.JavaPlugin;

public class JishlibPlugin extends JavaPlugin {

    @Override
    public final void onLoad() {
        JishLib.setInstance(this);
        pluginLoaded();
    }

    @Override
    public final void onEnable() {
        pluginEnabled();
    }

    @Override
    public final void reloadConfig() {
        pluginReloaded();
    }

    @Override
    public final void onDisable() {
        pluginDisabled();
    }

    public void pluginLoaded() {

    }

    public void pluginEnabled() {

    }

    public void pluginReloaded() {

    }

    public void pluginDisabled() {

    }
}
