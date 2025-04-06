package me.jishuna.jishlib;

import org.bukkit.plugin.java.JavaPlugin;

public class JishlibPlugin extends JavaPlugin {

    @Override
    public final void onLoad() {
        JishLib.setInstance(this);
        loaded();
    }

    @Override
    public final void onEnable() {
        enabled();
    }

    @Override
    public final void reloadConfig() {
        reloaded();
    }

    @Override
    public final void onDisable() {
        disabled();
    }

    public void loaded() {

    }

    public void enabled() {

    }

    public void reloaded() {

    }

    public void disabled() {

    }
}
