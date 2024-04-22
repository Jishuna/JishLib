package me.jishuna.jishlib.selection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import me.jishuna.jishlib.Feature;
import me.jishuna.jishlib.SpigotPlugin;
import me.jishuna.jishlib.util.BlockPos;

public class PlayerSelections implements Feature {
    private static PlayerSelections INSTANCE;

    public static PlayerSelections getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerSelections();
            SpigotPlugin.getInstance().getActiveFeatures().add(INSTANCE);
        }

        return INSTANCE;
    }

    private final Map<Player, Selection> selections = new ConcurrentHashMap<>();
    private final BukkitTask renderTask;

    private PlayerSelections() {
        Bukkit.getPluginManager().registerEvents(new SelectionListener(this), SpigotPlugin.getInstance());
        this.renderTask = Bukkit.getScheduler().runTaskTimerAsynchronously(SpigotPlugin.getInstance(), () -> {
            this.selections.forEach((player, selection) -> {
//                if (selection.isComplete()) {
//                    selection.getBounds().outline(player, Color.ORANGE);
//                }
            });
        }, 0, 3);
    }

    public void setFirstPosition(Player player, BlockPos pos) {
        this.selections.computeIfAbsent(player, k -> new Selection(player)).setPosition1(pos);
    }

    public void setSecondPosition(Player player, BlockPos pos) {
        this.selections.computeIfAbsent(player, k -> new Selection(player)).setPosition2(pos);
    }

    public boolean hasCompleteSelection(Player player) {
        Selection selection = this.selections.get(player);
        if (selection == null) {
            return false;
        }

        return selection.isComplete();
    }

    public Selection getSelection(Player player) {
        return this.selections.get(player);
    }

    public Selection clearSelection(Player player) {
        return this.selections.remove(player);
    }

    @Override
    public void cleanup() {
        this.renderTask.cancel();
        this.selections.clear();
        INSTANCE = null;
    }
}
