package me.jishuna.jishlib.conversation;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.jishuna.jishlib.JishLib;

public class ConversationAPI {
    private static ConversationManager manager;

    public static void initialize() {
        Preconditions.checkArgument(manager == null, "ConversationAPI already initialized!");
        Preconditions.checkArgument(JishLib.isInitialized(), "JishLib must be initialized first!");

        manager = new ConversationManager();
        Bukkit.getPluginManager().registerEvents(manager, JishLib.getPlugin());
    }

    protected static void addConversation(UUID id, Conversation conversation) {
        getInstance().conversations.put(id, conversation);
    }

    public static void clearConversation(UUID id) {
        getInstance().conversations.remove(id);
    }

    public static Conversation.Builder createConversation(Player player) {
        return new Conversation.Builder(player);
    }

    private static ConversationManager getInstance() {
        if (manager == null) {
            throw new IllegalStateException("ConversationAPI not initialized!");
        }
        return manager;
    }

    private ConversationAPI() {
    }

    static final class ConversationManager implements Listener {
        private final Map<UUID, Conversation> conversations = new ConcurrentHashMap<>();

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
                    this.conversations.remove(id);
                }
            });
        }

        @EventHandler
        public void onPlayerLeave(PlayerQuitEvent event) {
            this.conversations.remove(event.getPlayer().getUniqueId());
        }
    }
}
