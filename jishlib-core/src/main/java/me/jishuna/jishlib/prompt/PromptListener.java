package me.jishuna.jishlib.prompt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PromptListener implements Listener {
    private final PromptSystem system;

    public PromptListener(PromptSystem system) {
        this.system = system;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Prompt prompt = this.system.getPrompt(event.getPlayer());

        if (prompt == null) {
            return;
        }

        event.setCancelled(true);
        if (prompt.handleInput(event.getMessage())) {
            this.system.clearPrompt(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        this.system.clearPrompt(event.getPlayer());
    }
}
