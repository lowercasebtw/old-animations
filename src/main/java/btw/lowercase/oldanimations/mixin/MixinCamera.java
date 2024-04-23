package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Camera.class, priority = Integer.MAX_VALUE)
public abstract class MixinCamera {
    @Shadow
    protected abstract void moveBy(double d, double e, double f);

    @Inject(at = @At(value = "TAIL"), method = "update")
    private void update$old(BlockView blockView, Entity entity, boolean thirdPerson, boolean thirdPersonFront,
            float tickDelta,
            CallbackInfo ci) {
        if (!thirdPerson && !(entity instanceof LivingEntity && ((LivingEntity) entity).isSleeping())
                && OldAnimations.CONFIG.visualSettings.OLD_CAMERA_POSITION)
            this.moveBy(-0.05000000074505806, 0.0, 0.0);
    }
}