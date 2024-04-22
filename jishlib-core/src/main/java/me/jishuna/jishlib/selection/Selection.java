package me.jishuna.jishlib.selection;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import me.jishuna.jishlib.SpigotPlugin;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.util.BlockPos;
import me.jishuna.jishlib.util.Cuboid;

public class Selection {
    private static final ItemStack DISPLAY_ITEM = ItemBuilder.create(Material.PLAYER_HEAD).skullTexture("967a2f218a6e6e38f2b545f6c17733f4ef9bbb288e75402949c052189ee").build();

    private static double SCALE = 1 / 8d;
    private static double QUARTER_SCALE = SCALE / 4d;

    private final Player player;
    private BlockPos position1;
    private BlockPos position2;
    private Cuboid bounds;

    private final List<ItemDisplay> displays = new ArrayList<>(6);

    public Selection(Player player) {
        this.player = player;
    }

    public boolean isComplete() {
        return this.bounds != null;
    }

    public BlockPos getPosition1() {
        return this.position1;
    }

    public BlockPos getPosition2() {
        return this.position2;
    }

    public void setPosition1(BlockPos position1) {
        this.position1 = position1;
        updateBounds();
    }

    public void setPosition2(BlockPos position2) {
        this.position2 = position2;
        updateBounds();
    }

    public Cuboid getBounds() {
        return this.bounds;
    }

    private void updateBounds() {
        if (this.position1 == null || this.position2 == null) {
            this.bounds = null;
        } else {
            this.bounds = Cuboid.of(this.position1, this.position2);
            updateDisplays();
        }
    }

    private void updateDisplays() {
        if (this.displays.isEmpty()) {
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
            this.displays.add(createDisplay());
        }

        Location min = new Location(this.player.getWorld(), this.bounds.minX, this.bounds.minY, this.bounds.minZ);

        ItemDisplay display;
        Transformation transformation;

        display = this.displays.get(0);
        transformation = display.getTransformation();
        transformation.getScale().set(this.bounds.getWidthX() * 2, SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX() / 2, QUARTER_SCALE, 0));

        display = this.displays.get(1);
        transformation = display.getTransformation();
        transformation.getScale().set(this.bounds.getWidthX() * 2, SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX() / 2, QUARTER_SCALE, this.bounds.getWidthZ()));

        display = this.displays.get(2);
        transformation = display.getTransformation();
        transformation.getScale().set(this.bounds.getWidthX() * 2, SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX() / 2, this.bounds.getHeight() + QUARTER_SCALE, 0));

        display = this.displays.get(3);
        transformation = display.getTransformation();
        transformation.getScale().set(this.bounds.getWidthX() * 2, SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX() / 2, this.bounds.getHeight() + QUARTER_SCALE, this.bounds.getWidthZ()));

        display = this.displays.get(4);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, SCALE, this.bounds.getWidthZ() * 2);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(0, QUARTER_SCALE, this.bounds.getWidthZ() / 2));

        display = this.displays.get(5);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, SCALE, this.bounds.getWidthZ() * 2);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(0, this.bounds.getHeight() + QUARTER_SCALE, this.bounds.getWidthZ() / 2));

        display = this.displays.get(6);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, SCALE, this.bounds.getWidthZ() * 2);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX(), QUARTER_SCALE, this.bounds.getWidthZ() / 2));

        display = this.displays.get(7);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, SCALE, this.bounds.getWidthZ() * 2);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX(), this.bounds.getHeight() + QUARTER_SCALE, this.bounds.getWidthZ() / 2));

        display = this.displays.get(8);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, this.bounds.getHeight() * 2 + SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(0, this.bounds.getHeight() + QUARTER_SCALE, 0));

        display = this.displays.get(9);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, this.bounds.getHeight() * 2 + SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX(), this.bounds.getHeight() + QUARTER_SCALE, 0));

        display = this.displays.get(10);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, this.bounds.getHeight() * 2 + SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(0, this.bounds.getHeight() + QUARTER_SCALE, this.bounds.getWidthZ()));

        display = this.displays.get(11);
        transformation = display.getTransformation();
        transformation.getScale().set(SCALE, this.bounds.getHeight() * 2 + SCALE, SCALE);
        display.setTransformation(transformation);
        display.teleport(min.clone().add(this.bounds.getWidthX(), this.bounds.getHeight() + QUARTER_SCALE, this.bounds.getWidthZ()));
    }

    private ItemDisplay createDisplay() {
        return this.player.getWorld().spawn(this.player.getLocation(), ItemDisplay.class, e -> {
            e.setItemStack(DISPLAY_ITEM);
            e.setVisibleByDefault(false);
            this.player.showEntity(SpigotPlugin.getInstance(), e);
        });
    }
}
