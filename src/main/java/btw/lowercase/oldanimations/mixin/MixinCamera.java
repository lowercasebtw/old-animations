package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.config.VisualSettingsConfig;
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
    @Shadow private float cameraY;

    @Shadow private float lastCameraY;

    @Shadow protected abstract void moveBy(double x, double y, double z);

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V", shift = At.Shift.BEFORE))
    private void update$old$smoothSneaking(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (!OldAnimations.CONFIG.visualSettings.SMOOTH_SNEAK_ANIMATION) {
            lastCameraY = cameraY;
            cameraY = focusedEntity.getStandingEyeHeight();
        }
    }

    @Inject(method = "update", at = @At(value = "TAIL"))
    private void update$old$cameraType(BlockView blockView, Entity entity, boolean thirdPerson, boolean thirdPersonFront, float tickDelta, CallbackInfo ci) {
        if (OldAnimations.CONFIG.visualSettings.CAMERA_VERSION == VisualSettingsConfig.CameraVersion.LATEST)
            return;

        // TODO: Fix accuracies for different states, like in bed, in third person, etc...

        VisualSettingsConfig.CameraVersion cameraVersion = OldAnimations.CONFIG.visualSettings.CAMERA_VERSION;
        int ordinal = cameraVersion.ordinal();

        if (ordinal <= VisualSettingsConfig.CameraVersion._1_14_to_1_14_3.ordinal()) {
            // <= 1.14.3
            if (!thirdPerson && !(entity instanceof LivingEntity && ((LivingEntity)entity).isSleeping()))
                this.moveBy(-0.05000000074505806, 0.0, 0.0);
            if (ordinal <= VisualSettingsConfig.CameraVersion._1_9_to_1_13_2.ordinal()) {
                // <= 1.13.2
                this.moveBy(0.1, 0.0, 0.0);
                if (ordinal == VisualSettingsConfig.CameraVersion._BELOW_OR_1_8.ordinal()) {
                    // == 1.8
                    this.moveBy(-0.15, 0, 0); // unfixing parallax
                }
            }
        }

        else if (ordinal == VisualSettingsConfig.CameraVersion._1_14_4_to_1_20.ordinal()) {
            // TODO: _1_14_4_to_1_20
            // tf changed?
            return;
        }

        else if (ordinal == VisualSettingsConfig.CameraVersion.BEDROCK.ordinal()) {
            // TODO
            return;
        }
    }
}