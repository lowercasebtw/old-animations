package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.IRenderedOverlay;
import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntityRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements FeatureRendererContext<S, M> {
    @Shadow
    protected abstract float getAnimationCounter(S state);

    @Shadow
    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return 0;
    }

    protected MixinLivingEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("HEAD"), cancellable = true)
    public void hasLabel(T livingEntity, double d, CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimations.CONFIG.qolSettings.SHOW_NAMETAG_THIRD_PERSON && livingEntity == MinecraftClient.getInstance().getCameraEntity()) {
            cir.setReturnValue(true);
        }
    }

    // TODO: Fix armor damage color
    // Code used from/inspired by:
    // Licensed CC0-1.0
    // https://modrinth.com/user/rizecookey
    // https://modrinth.com/mod/cookeymod
    // https://github.com/rizecookey/CookeyMod/tree/1.20.6/src/main
    // net/rizecookey/cookeymod/mixin/client/LivingEntityRendererMixin.java
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Redirect(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"))
    public void render$old$armorHurtColor(FeatureRenderer instance, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, EntityRenderState entityRenderState, float yaw, float pitch) {
        if ((instance instanceof IRenderedOverlay<?>) && OldAnimations.CONFIG.qolSettings.RENDER_HIT_COLOR_ARMOR) {
            int coords = getOverlay((S) entityRenderState, this.getAnimationCounter((S) entityRenderState));
            ((IRenderedOverlay<S>) instance).oldanimations$render(matrixStack, vertexConsumerProvider, (S) entityRenderState, coords, light, yaw, pitch);
        } else {
            instance.render(matrixStack, vertexConsumerProvider, light, entityRenderState, yaw, pitch);
        }
    }
}
