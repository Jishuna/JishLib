package me.jishuna.jishlib.conversation.prompt;

import java.util.function.Consumer;
import java.util.function.Supplier;
import me.jishuna.jishlib.conversation.Conversation;

public class CallbackPrompt extends Prompt {
    private final Supplier<String> message;
    private final Consumer<String> callback;

    public CallbackPrompt(Supplier<String> message, Consumer<String> callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public boolean processInput(Conversation conversation, String input) {
        this.callback.accept(input);
        return true;
    }

    @Override
    public void start(Conversation conversation) {
        conversation.getEntity().sendMessage(this.message.get());
    }
}
