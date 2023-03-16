package me.jishuna.jishlib;

public class Utils {
	private Utils() {
	}
	
	public static boolean isWithinBounds(int value, int min, int max) {
		return value >= min && value <= max;
	}
}
