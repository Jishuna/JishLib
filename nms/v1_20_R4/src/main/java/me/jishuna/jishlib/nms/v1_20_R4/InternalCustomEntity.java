package me.jishuna.jishlib.nms.v1_20_R4;

import java.lang.reflect.Field;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftSound;
import me.jishuna.jishlib.nms.entity.goal.DefaultGoal;
import me.jishuna.jishlib.util.ReflectionHelper;

public class InternalCustomEntity extends Zombie {
    private static final Field DIMENSIONS_FIELD = ReflectionHelper.getField(Entity.class, EntityDimensions.class, 0);

    private final EntityType<?> type;

    private CraftCustomEntity craftEntity;
    private SoundEvent ambientSound = SoundEvents.EMPTY;
    private SoundEvent hurtSound = SoundEvents.EMPTY;
    private SoundEvent deathSound = SoundEvents.EMPTY;
    private SoundEvent stepSound = SoundEvents.EMPTY;

    public InternalCustomEntity(Level world, EntityType<?> type) {
        super(EntityType.ZOMBIE, world);
        this.type = type;

        try {
            DIMENSIONS_FIELD.set(this, type.getDimensions());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(getId(), getUUID(), getX(), getY(), getZ(), getXRot(), getYRot(), this.type, 0, getDeltaMovement(), getYHeadRot());
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public CraftCustomEntity getBukkitEntity() {
        if (this.craftEntity == null) {
            this.craftEntity = new CraftCustomEntity(level().getCraftServer(), this);
        }

        return this.craftEntity;
    }

    @Override
    public boolean isSunSensitive() {
        return false;
    }

    public void addGoal(int priority, DefaultGoal goal) {
        GoalHelper.addGoal(this, priority, goal);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.ambientSound;
    }

    public void setAmbientSound(Sound sound) {
        this.ambientSound = CraftSound.bukkitToMinecraft(sound);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.hurtSound;
    }

    public void setHurtSound(Sound sound) {
        this.hurtSound = CraftSound.bukkitToMinecraft(sound);
    }

    @Override
    public SoundEvent getDeathSound() {
        return this.deathSound;
    }

    public void setDeathSound(Sound sound) {
        this.deathSound = CraftSound.bukkitToMinecraft(sound);
    }

    @Override
    protected SoundEvent getStepSound() {
        return this.stepSound;
    }

    public void setStepSound(Sound sound) {
        this.stepSound = CraftSound.bukkitToMinecraft(sound);
    }
}
