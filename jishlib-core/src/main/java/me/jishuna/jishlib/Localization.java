package me.jishuna.jishlib;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class Localization {
	private static final Localization instance = new Localization();
	private final Map<String, String> localizationMap = new HashMap<>();

	public String localize(String key) {
		return this.localizationMap.getOrDefault(key, "Missing key: " + key);
	}

	public String localize(String key, Object... format) {
		return MessageFormat.format(localize(key), format);
	}

	public void parse(YamlConfiguration config) {
		this.localizationMap.clear();
		for (String key : config.getKeys(true)) {
			if (!config.isString(key))
				continue;

			this.localizationMap.put(key, StringUtils.parseToLegacy(config.getString(key)));
		}
	}

	public static Localization getInstance() {
		return instance;
	}

}
