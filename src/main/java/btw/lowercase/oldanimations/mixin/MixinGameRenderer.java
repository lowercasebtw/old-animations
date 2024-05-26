package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinGameRenderer {
    @Mutable
    @Shadow @Final private OverlayTexture overlayTexture;

    @Unique
    public float bobbingTilt = 0.0f;

    @Unique
    public float prevBobbingTilt = 0.0f;

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void tiltViewWhenHurt$old$noTilt(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (OldAnimations.CONFIG.visualSettings.NO_DAMAGE_TILT)
            ci.cancel();
    }

    @Redirect(method = "tiltViewWhenHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDamageTiltYaw()F"))
    private float tiltViewWhenHurt$old(LivingEntity instance) {
        return OldAnimations.CONFIG.legacySettings.OLD_DAMAGE_TILT ? 0.0f :  instance.getDamageTiltYaw();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick$overlayTextureReload(CallbackInfo ci) {
        if (OldAnimations.previousHitColor != OldAnimations.CONFIG.qolSettings.HIT_COLOR) {
            OldAnimations.previousHitColor = OldAnimations.CONFIG.qolSettings.HIT_COLOR;
            overlayTexture = new OverlayTexture();
        }
    }

//    @Inject(method = "bobView", at = @At("TAIL"))
//    private void bobView$old(MatrixStack matrixStack, float tickDelta, CallbackInfo ci) {
//        if (!OldAnimations.CONFIG.bugFixes.VERTICAL_BOBBING)
//            return;
//        if (!(this.client.getCameraEntity() instanceof PlayerEntity))
//            return;
//        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(tickDelta, prevBobbingTilt, bobbingTilt)));
//    }
//
//    @Inject(method = "render", at = @At("HEAD"))
//    public void render$old$bobbingTiltFix(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
//        if (!OldAnimations.CONFIG.bugFixes.VERTICAL_BOBBING || client.player == null)
//            return;
//        float g = 0.0f;
//        if (!client.player.isOnGround() && !(client.player.getHealth() <= 0.0F))
//            g = (float)(Math.atan(-client.player.getVelocity().y * 0.20000000298023224) * 15.0);
//        prevBobbingTilt = bobbingTilt;
//        bobbingTilt += (g - bobbingTilt) * 0.8F;
//    }
}
