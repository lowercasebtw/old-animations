package btw.lowercase.oldanimations.mixin.entity;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.accessor.BobbingAccessor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = PlayerEntity.class, priority = Integer.MAX_VALUE)
public abstract class MixinPlayerEntity extends LivingEntity {
    @Shadow
    @Final
    public static EntityDimensions STANDING_DIMENSIONS;

    @Shadow
    @Final
    private static Map<EntityPose, EntityDimensions> POSE_DIMENSIONS;

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    // NOTE: It's annoying that you have to extend a class to get its parent method/fields
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setMovementSpeed(F)V", shift = At.Shift.AFTER))
    public void tickMovement(CallbackInfo ci) {
        if (!OldAnimations.CONFIG.bugFixes.VERTICAL_BOBBING_TILT)
            return;
        BobbingAccessor bobbingAccessor = (BobbingAccessor) this;
        float g = this.isOnGround() || this.getHealth() <= 0.0f ? 0.0f : (float) (Math.atan(-this.getVelocity().y * (double) 0.2f) * 15.0);
        bobbingAccessor.tiltingFix$setBobbingTilt(bobbingAccessor.tiltingFix$getBobbingTilt() + (g - bobbingAccessor.tiltingFix$getBobbingTilt()) * 0.8f);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
    public void attack$old$combatSounds(World world, PlayerEntity playerEntity, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        if (OldAnimations.CONFIG.legacySettings.DISABLE_COMBAT_SOUNDS && (sound == SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK || sound == SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP || sound == SoundEvents.ENTITY_PLAYER_ATTACK_CRIT || sound == SoundEvents.ENTITY_PLAYER_ATTACK_STRONG || sound == SoundEvents.ENTITY_PLAYER_ATTACK_WEAK || sound == SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE))
            return;
        world.playSound(playerEntity, x, y, z, sound, category, volume, pitch);
    }

    @ModifyConstant(method = "attack", constant = @Constant(floatValue = 0.0f, ordinal = 4))
    public float attack$old$alwaysSharpParticles(float value) {
        return OldAnimations.CONFIG.qolSettings.ALWAYS_SHARP_PARTICLES ? -1 : value;
    }

    @Inject(method = "getBaseDimensions", at = @At("HEAD"), cancellable = true)
    private void getBaseDimensions$old$sneaking(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (!OldAnimations.CONFIG.visualSettings.OLD_SNEAK_ANIMATION || pose != EntityPose.CROUCHING)
            return;
        // TODO: Fix camera, so it wont be considered cheating
        cir.setReturnValue(POSE_DIMENSIONS.getOrDefault(OldAnimations.CONFIG.visualSettings.OLD_SNEAK_MECHANIC ? null : pose, STANDING_DIMENSIONS).withEyeHeight(1.54f));
    }

    // addEnchantedHitParticles

    // addCritParticles

    // spawnSweepAttackParticles

    // TODO: Particle Multiplier
}