package me.jishuna.jishlib.util;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectBuilder {
    private final PotionEffectType type;
    private int duration;
    private int level;
    private boolean ambient = false;
    private boolean showParticles = true;
    private boolean showIcon = true;

    private EffectBuilder(PotionEffectType type, int duration, int level) {
        this.type = type;
        this.duration = duration;
        this.level = level;
    }

    public static EffectBuilder of(PotionEffectType type) {
        return new EffectBuilder(type, 0, 0);
    }

    public static EffectBuilder of(PotionEffectType type, int duration, int level) {
        return new EffectBuilder(type, duration, level);
    }

    public EffectBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public EffectBuilder level(int level) {
        this.level = level;
        return this;
    }

    public EffectBuilder ambient() {
        this.ambient = true;
        return this;
    }

    public EffectBuilder hideParticles() {
        this.showParticles = false;
        return this;
    }

    public EffectBuilder hideIcon() {
        this.showIcon = false;
        return this;
    }

    public PotionEffect build() {
        return new PotionEffect(this.type, this.duration, this.level, this.ambient, this.showParticles, this.showIcon);
    }
}
