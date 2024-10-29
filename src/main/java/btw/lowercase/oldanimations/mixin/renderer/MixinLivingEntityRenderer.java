package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.IRenderedOverlay;
import btw.lowercase.oldanimations.OldAnimations;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntityRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements FeatureRendererContext<S, M> {
    @Shadow
    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return 0;
    }

    @Shadow
    protected abstract float getAnimationCounter(S state);

    protected MixinLivingEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("HEAD"), cancellable = true)
    public void hasLabel(T livingEntity, double d, CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimations.CONFIG.qolSettings.SHOW_NAMETAG_THIRD_PERSON && livingEntity == MinecraftClient.getInstance().getCameraEntity()) {
            cir.setReturnValue(true);
        }
    }

    // Code used from/inspired by:
    // Licensed CC0-1.0
    // https://modrinth.com/user/rizecookey
    // https://modrinth.com/mod/cookeymod
    // https://github.com/rizecookey/CookeyMod/blob/1.21/src/main/java/net/rizecookey/cookeymod/mixin/client/LivingEntityRendererMixin.java
    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"))
    public void renderWithOverlay(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci, @Local FeatureRenderer<S, M> renderLayer) {
        if (OldAnimations.CONFIG.qolSettings.RENDER_HIT_COLOR_ARMOR && renderLayer instanceof IRenderedOverlay renderedOverlay) {
            int overlayCoords = getOverlay(livingEntityRenderState, this.getAnimationCounter(livingEntityRenderState));
            renderedOverlay.oldanimations$setArmorOverlayCoords(overlayCoords);
        }
    }
}
