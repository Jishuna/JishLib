package me.jishuna.jishlib;

import org.bukkit.Chunk;

public class Utils {
	private Utils() {
	}

	public static boolean isWithinBounds(int value, int min, int max) {
		return value >= min && value <= max;
	}

	public static long getChunkKey(Chunk chunk) {
		return ((long) chunk.getZ() << 32) | (chunk.getX() & 0xFFFFFFFFL);
	}
}
