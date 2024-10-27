package btw.lowercase.oldanimations.mixin.entity;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.ViewBobbingStorage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, priority = Integer.MAX_VALUE)
public abstract class MixinLivingEntity implements ViewBobbingStorage {
    @Unique
    private float bobbingTilt = 0.0f;

    @Unique
    private float previousBobbingTilt = 0.0f;

    @Override
    public void tiltingFix$setBobbingTilt(float bobbingTilt) {
        this.bobbingTilt = bobbingTilt;
    }

    @Override
    public void tiltingFix$setPreviousBobbingTilt(float previousBobbingTilt) {
        this.previousBobbingTilt = previousBobbingTilt;
    }

    @Override
    public float tiltingFix$getBobbingTilt() {
        return bobbingTilt;
    }

    @Override
    public float tiltingFix$getPreviousBobbingTilt() {
        return previousBobbingTilt;
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tickStatusEffects()V", shift = At.Shift.BEFORE))
    public void baseTick(CallbackInfo ci) {
        if (!OldAnimations.CONFIG.bugFixes.VERTICAL_BOBBING_TILT)
            return;
        this.tiltingFix$setPreviousBobbingTilt(this.tiltingFix$getBobbingTilt());
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;abs(F)F"))
    private float tick$old$backwardsWalking(float value) {
        return OldAnimations.CONFIG.legacySettings.OLD_BACKWARDS_WALKING ? 0.0f : MathHelper.abs(value);
    }
}
