package me.jishuna.jishlib.conversation;

import java.util.function.Function;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class ValidatedPrompt extends StringPrompt {
    private final String message;
    private final Function<String, Boolean> callback;

    public ValidatedPrompt(String message, Function<String, Boolean> callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return this.message;
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (Boolean.TRUE.equals(this.callback.apply(input))) {
            return Prompt.END_OF_CONVERSATION;
        }
        return this;
    }
}
