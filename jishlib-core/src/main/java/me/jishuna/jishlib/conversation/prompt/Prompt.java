package me.jishuna.jishlib.conversation.prompt;

import me.jishuna.jishlib.conversation.Conversation;

public abstract class Prompt {

    public abstract boolean processInput(Conversation conversation, String input);

    public abstract void start(Conversation conversation);
}
