package me.jishuna.jishlib.conversation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.conversation.prompt.Prompt;

public class Conversation {
    private final Player entity;
    private final Queue<Prompt> queue;
    private Prompt currentPrompt;

    public Conversation(Player entity, List<Prompt> prompts) {
        this.entity = entity;
        this.queue = new ArrayDeque<>(prompts);

        startNextPrompt();
    }

    public void processInput(String input) {
        if (this.currentPrompt.processInput(this, input)) {
            startNextPrompt();
        }
    }

    public Player getEntity() {
        return this.entity;
    }

    public boolean isComplete() {
        return this.currentPrompt == null;
    }

    private void startNextPrompt() {
        if (this.queue.isEmpty()) {
            this.currentPrompt = null;
        } else {
            this.currentPrompt = this.queue.poll();
            this.currentPrompt.start(this);
        }
    }

    public static class Builder {
        private final Player player;
        private final List<Prompt> prompts = new ArrayList<>();

        protected Builder(Player player) {
            this.player = player;
        }

        public Builder prompt(Prompt prompt) {
            this.prompts.add(prompt);
            return this;
        }

        public void begin() {
            ConversationAPI.addConversation(this.player.getUniqueId(), new Conversation(this.player, this.prompts));
        }

    }
}
