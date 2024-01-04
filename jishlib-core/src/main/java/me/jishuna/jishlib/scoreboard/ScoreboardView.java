package me.jishuna.jishlib.scoreboard;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class ScoreboardView {
    private static final ChatColor[] TEAM_COLORS = { ChatColor.AQUA, ChatColor.BLACK, ChatColor.BLUE,
            ChatColor.DARK_AQUA, ChatColor.DARK_BLUE, ChatColor.DARK_GRAY,
            ChatColor.DARK_GREEN, ChatColor.DARK_PURPLE, ChatColor.DARK_RED,
            ChatColor.GOLD, ChatColor.GRAY, ChatColor.GREEN,
            ChatColor.LIGHT_PURPLE, ChatColor.RED, ChatColor.WHITE };
    private static final int MAX_LINES = 15;

    private final ScoreboardWrapper scoreboard;
    private final Objective objective;
    private final Team[] teams;
    private final int lines;

    protected ScoreboardView(ScoreboardWrapper scoreboard, String name, int lines) {
        Preconditions.checkArgument(lines > 0, "Must have at least one line");
        Preconditions.checkArgument(lines <= MAX_LINES, "Cannot have more than 15 lines");

        this.scoreboard = scoreboard;
        this.objective = scoreboard.getBukkitScoreboard().registerNewObjective(ChatColor.stripColor(name), "dummy", name);
        this.teams = new Team[lines];
        this.lines = lines;

        makeTeams();
    }

    public Objective getBukkitObjective() {
        return this.objective;
    }

    public ScoreboardWrapper getScoreboard() {
        return this.scoreboard;
    }

    public String getTitle() {
        return this.objective.getDisplayName();
    }

    public void setTitle(String title) {
        this.objective.setDisplayName(title);
    }

    public String getLine(int index) {
        return this.teams[index].getPrefix();
    }

    public void setLine(int index, String line) {
        this.teams[index].setPrefix(line);
    }

    private void makeTeams() {
        int score = this.lines;

        for (int i = 0; i < this.lines; i++) {
            Team team = this.scoreboard.getBukkitScoreboard().registerNewTeam("line_" + i);
            String entry = TEAM_COLORS[i].toString();
            team.addEntry(entry);
            this.objective.getScore(entry).setScore(score--);
            this.teams[i] = team;
        }
    }

}
