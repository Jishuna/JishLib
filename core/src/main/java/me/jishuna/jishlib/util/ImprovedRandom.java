package me.jishuna.jishlib.util;

import java.util.Random;

public class ImprovedRandom extends Random {
    private static final long serialVersionUID = 1L;

    public double between(double min, double max, int decimals) {
        double factor = Math.pow(10, decimals);

        int minInt = (int) (min * factor);
        int maxInt = (int) (max * factor) + 1;

        return nextInt(minInt, maxInt) / factor;
    }
}
