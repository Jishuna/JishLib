package me.jishuna.jishlib.conversation;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.conversation.Conversation.Builder;

public class ConversationManager implements Listener {
    private final Map<UUID, Conversation> conversations = new ConcurrentHashMap<>();

    protected void addConversation(UUID id, Conversation conversation) {
        this.conversations.put(id, conversation);
    }

    public void clearConversation(UUID id) {
        this.conversations.remove(id);
    }

    public Builder createConversation(Player player) {
        return new Conversation.Builder(this, player);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        Conversation conversation = this.conversations.get(id);
        if (conversation == null) {
            return;
        }

        event.setCancelled(true);

        JishLib.run(() -> {
            conversation.processInput(event.getMessage());
            if (conversation.isComplete()) {
                clearConversation(id);
            }
        });
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        clearConversation(event.getPlayer().getUniqueId());
    }
}
