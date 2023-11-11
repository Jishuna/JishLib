package me.jishuna.jishlib.conversation.prompt;

import java.util.function.Function;
import java.util.function.Supplier;
import me.jishuna.jishlib.conversation.Conversation;

public class ValidatedPrompt extends Prompt {
    private final Supplier<String> message;
    private final Function<String, Boolean> callback;

    public ValidatedPrompt(Supplier<String> message, Function<String, Boolean> callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public boolean processInput(Conversation conversation, String input) {
        return this.callback.apply(input);
    }

    @Override
    public void start(Conversation conversation) {
        conversation.getEntity().sendMessage(this.message.get());
    }
}
