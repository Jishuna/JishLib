package me.jishuna.jishlib.nms.entity.goal;

public class MeleeAttackGoal implements DefaultGoal {
    private final double speed;
    private final boolean pauseWhenIdle;

    public MeleeAttackGoal(double speed, boolean pauseWhenIdle) {
        this.speed = speed;
        this.pauseWhenIdle = pauseWhenIdle;
    }

    public double getSpeed() {
        return this.speed;
    }

    public boolean shouldPauseWhenIdle() {
        return this.pauseWhenIdle;
    }
}
