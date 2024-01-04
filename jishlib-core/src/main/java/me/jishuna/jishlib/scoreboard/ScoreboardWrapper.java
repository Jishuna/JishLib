package me.jishuna.jishlib.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardWrapper {
    private final Scoreboard internal = Bukkit.getScoreboardManager().getNewScoreboard();
    private ScoreboardView active;

    public ScoreboardView createView(String name, int lines) {
        return createView(name, lines, false);
    }

    public ScoreboardView createView(String name, int lines, boolean setActive) {
        ScoreboardView view = new ScoreboardView(this, name, lines);
        if (setActive) {
            setActiveView(view);
        }

        return view;
    }

    public ScoreboardView getActiveView() {
        return this.active;
    }

    public void setActiveView(ScoreboardView view) {
        this.active = view;

        if (view == null) {
            this.internal.clearSlot(DisplaySlot.SIDEBAR);
        } else {
            view.getBukkitObjective().setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    public Scoreboard getBukkitScoreboard() {
        return this.internal;
    }
}
