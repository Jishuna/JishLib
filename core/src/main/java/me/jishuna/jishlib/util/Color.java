package me.jishuna.jishlib.util;

import net.kyori.adventure.text.format.TextColor;

public class Color {
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);

    public final int red;
    public final int green;
    public final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color interpolate(Color other, float amount) {
        int r = (int) (this.red + (other.red - this.red) * amount);
        int g = (int) (this.green + (other.green - this.green) * amount);
        int b = (int) (this.blue + (other.blue - this.blue) * amount);

        return new Color(r, g, b);
    }

    public Color darken(float amount) {
        return interpolate(BLACK, amount);
    }

    public Color lighten(float amount) {
        return interpolate(WHITE, amount);
    }

    public TextColor asTextColor() {
        return TextColor.color(this.red, this.green, this.blue);
    }
}
