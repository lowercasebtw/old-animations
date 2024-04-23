package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LivingEntity.class, priority = Integer.MAX_VALUE)
public abstract class MixinLivingEntity {
    // TODO: tiltScreen

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;abs(F)F"))
    private float tick$old(float value) {
        return OldAnimations.CONFIG.visualSettings.OLD_BACKWARDS_WALKING ? 0.0f : MathHelper.abs(value);
    }
}
