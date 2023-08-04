package me.jishuna.jishlib;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.jishlib.util.StringUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent.Builder;
import net.kyori.adventure.text.TextReplacementConfig;

@Deprecated
public class Localization {
    private static final Pattern PATTERN = Pattern.compile("%[^%]*%");
    private static final Localization instance = new Localization();

    private final Map<String, String> localizationMap = new HashMap<>();
    private final Map<Component, Component> componentMap = new HashMap<>();
    private YamlConfiguration config;

    public String localize(String key) {
        return this.localizationMap.computeIfAbsent(key, mapKey -> StringUtils.miniMessageToLegacy(config.getString(mapKey, "Missing key: " + mapKey)));
    }

    public String localize(String key, Object... format) {
        return MessageFormat.format(localize(key), format);
    }

    public List<String> localizeAll(String baseKey) {
        List<String> strings = new ArrayList<>();
        int index = 1;
        String key = baseKey + "-" + index;

        while (this.config.isSet(key)) {
            strings.add(localize(key));
            key = baseKey + "-" + ++index;
        }
        
        return strings;
    }

    public Component localizeComponent(Component component) {
        return componentMap.computeIfAbsent(component, key -> component.replaceText(TextReplacementConfig.builder().match(PATTERN).replacement(this::replacePlaceholders).build()));
    }

    private Component replacePlaceholders(MatchResult result, Builder builder) {
        String key = result.group().replace("%", "");
        return StringUtils.fromMiniMessage(config.getString(key, "Missing key: " + key));
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
        this.localizationMap.clear();
        this.componentMap.clear();
    }

    public static Localization getInstance() {
        return instance;
    }

}
