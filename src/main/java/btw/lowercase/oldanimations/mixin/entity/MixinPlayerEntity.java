package btw.lowercase.oldanimations.mixin.entity;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.ViewBobbingStorage;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
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
    private void tickMovement(CallbackInfo ci) {
        if (OldAnimations.CONFIG.bugFixes.VERTICAL_BOBBING_TILT) {
            ViewBobbingStorage bobbingAccessor = (ViewBobbingStorage) this;
            float g = this.isOnGround() || this.getHealth() <= 0.0F ? 0.0F : (float) (Math.atan(-this.getVelocity().y * (double) 0.2F) * 15.0F);
            bobbingAccessor.tiltingFix$setBobbingTilt(bobbingAccessor.tiltingFix$getBobbingTilt() + (g - bobbingAccessor.tiltingFix$getBobbingTilt()) * 0.8F);
        }
    }

    @WrapWithCondition(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
    private boolean attack$old$combatSounds(World world, PlayerEntity playerEntity, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        return !(OldAnimations.CONFIG.legacySettings.DISABLE_COMBAT_SOUNDS && (sound == SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK || sound == SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP || sound == SoundEvents.ENTITY_PLAYER_ATTACK_CRIT || sound == SoundEvents.ENTITY_PLAYER_ATTACK_STRONG || sound == SoundEvents.ENTITY_PLAYER_ATTACK_WEAK || sound == SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE));
    }

    @ModifyConstant(method = "attack", constant = @Constant(floatValue = 0.0F, ordinal = 4))
    private float attack$old$alwaysSharpParticles(float original) {
        if (OldAnimations.CONFIG.qolSettings.ALWAYS_SHARP_PARTICLES) {
            return -1;
        } else {
            return original;
        }
    }

    @Inject(method = "getBaseDimensions", at = @At("HEAD"), cancellable = true)
    private void getBaseDimensions$old$sneaking(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (OldAnimations.CONFIG.visualSettings.OLD_SNEAK_ANIMATION && pose == EntityPose.CROUCHING) {
            // TODO: Fix camera, so it wont be considered cheating
            cir.setReturnValue(POSE_DIMENSIONS.getOrDefault(OldAnimations.CONFIG.visualSettings.OLD_SNEAK_MECHANIC ? null : pose, STANDING_DIMENSIONS).withEyeHeight(1.54F));
        }
    }

    // addEnchantedHitParticles

    // addCritParticles

    // spawnSweepAttackParticles

    // TODO: Particle Multiplier
}