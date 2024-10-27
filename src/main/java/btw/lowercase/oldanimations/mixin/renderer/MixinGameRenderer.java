package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.accessor.BobbingAccessor;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinGameRenderer {
    @Shadow
    @Final
    MinecraftClient client;

    @Mutable
    @Shadow
    @Final
    private OverlayTexture overlayTexture;

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void tiltViewWhenHurt$old$noTilt(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (OldAnimations.CONFIG.visualSettings.NO_DAMAGE_TILT)
            ci.cancel();
    }

    @Redirect(method = "tiltViewWhenHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDamageTiltYaw()F"))
    private float tiltViewWhenHurt$old(LivingEntity instance) {
        return OldAnimations.CONFIG.legacySettings.OLD_DAMAGE_TILT ? 0.0f : instance.getDamageTiltYaw();
    }

    // NOTE: This way is hacky to allow me to check when a config setting has changed
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick$overlayTextureReload(CallbackInfo ci) {
        if (OldAnimations.previousHitColor != OldAnimations.CONFIG.qolSettings.HIT_COLOR) {
            overlayTexture = new OverlayTexture();
            OldAnimations.previousHitColor = OldAnimations.CONFIG.qolSettings.HIT_COLOR;
        }
    }

    @WrapWithCondition(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    public boolean renderWorld$qol$minimalBobbing(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        return !OldAnimations.CONFIG.qolSettings.MINIMAL_VIEW_BOBBING;
    }

    @Inject(method = "bobView", at = @At("TAIL"))
    private void bobView$bug$jumpTilt(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!OldAnimations.CONFIG.bugFixes.VERTICAL_BOBBING_TILT || !(this.client.getCameraEntity() instanceof PlayerEntity playerEntity))
            return;
        BobbingAccessor bobbingAccessor = (BobbingAccessor) playerEntity;
        float j = MathHelper.lerp(tickDelta, bobbingAccessor.tiltingFix$getPreviousBobbingTilt(), bobbingAccessor.tiltingFix$getBobbingTilt());
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(j));
    }
}
