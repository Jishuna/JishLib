package me.jishuna.jishlib;

import org.bukkit.Color;

public class StringUtils {
	private StringUtils() {
	}

	public static Color parseColor(String input, Color def) {
		if (input == null) {
			return def;
		}

		String[] parts = input.split(",");
		int red;
		int green;
		int blue;

		if (parts.length < 3) {
			return def;
		}

		try {
			red = Integer.parseInt(parts[0]);
			green = Integer.parseInt(parts[1]);
			blue = Integer.parseInt(parts[2]);
		} catch (NumberFormatException ex) {
			return def;
		}

		if (!Utils.isWithinBounds(red, 0, 255) || !Utils.isWithinBounds(green, 0, 255)
				|| !Utils.isWithinBounds(blue, 0, 255)) {
			return def;
		}

		return Color.fromRGB(red, green, blue);
	}
}
