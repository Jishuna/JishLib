package me.jishuna.jishlib.conversation;

import java.util.function.Consumer;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class CallbackPrompt extends StringPrompt {
    private final String message;
    private final Consumer<String> callback;

    public CallbackPrompt(String message, Consumer<String> callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return this.message;
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        callback.accept(input);
        return Prompt.END_OF_CONVERSATION;
    }
}
