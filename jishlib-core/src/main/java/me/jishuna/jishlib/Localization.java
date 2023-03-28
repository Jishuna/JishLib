package me.jishuna.jishlib;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class Localization {
	private static final Localization instance = new Localization();

	private final Map<String, String> localizationMap = new HashMap<>();
	private YamlConfiguration config;

	public String localize(String key) {
		return this.localizationMap.computeIfAbsent(key,
				mapKey -> StringUtils.parseToLegacy(config.getString(mapKey, "Missing key: " + mapKey)));
	}

	public String localize(String key, Object... format) {
		return MessageFormat.format(localize(key), format);
	}

	public void setConfig(YamlConfiguration config) {
		this.config = config;
		this.localizationMap.clear();
	}

	public static Localization getInstance() {
		return instance;
	}

}
