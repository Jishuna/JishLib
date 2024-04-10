package me.jishuna.jishlib.prompt;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.JishLib;

public class PromptSystem {
    private static PromptSystem INSTANCE;

    public static PromptSystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PromptSystem();
        }

        return INSTANCE;
    }

    private final Map<UUID, Prompt> prompts = new ConcurrentHashMap<>();

    public PromptSystem() {
        PromptListener listener = new PromptListener(this);
        Bukkit.getPluginManager().registerEvents(listener, JishLib.getPlugin());
    }

    public void startPrompt(HumanEntity entity, Prompt prompt) {
        this.prompts.put(entity.getUniqueId(), prompt);
    }

    public Prompt getPrompt(HumanEntity entity) {
        return getPrompt(entity.getUniqueId());
    }

    public Prompt getPrompt(UUID id) {
        return this.prompts.get(id);
    }

    public void clearPrompt(HumanEntity entity) {
        this.prompts.remove(entity.getUniqueId());
    }

    public void clearPrompt(UUID id) {
        this.prompts.remove(id);
    }
}
