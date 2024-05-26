package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.RenderedOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntityRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected MixinLivingEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Shadow
    public static int getOverlay(LivingEntity livingEntity, float tickDelta) { return 0; }

    @Shadow protected abstract float getAnimationCounter(T entity, float tickDelta);

    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("RETURN"), cancellable = true)
    public void hasLabel(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimations.CONFIG.qolSettings.SHOW_NAMETAG_THIRD_PERSON && livingEntity == MinecraftClient.getInstance().getCameraEntity())
            cir.setReturnValue(true);
    }

    // Code used from/inspired by:
    // Licensed CC0-1.0
    // https://modrinth.com/user/rizecookey
    // https://modrinth.com/mod/cookeymod
    // https://github.com/rizecookey/CookeyMod/tree/1.20.6/src/main
    // net/rizecookey/cookeymod/mixin/client/LivingEntityRendererMixin.java
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Redirect(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/Entity;FFFFFF)V"))
    public void render$old$armorHurtColor(FeatureRenderer instance, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, Entity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if ((instance instanceof RenderedOverlay) && OldAnimations.CONFIG.qolSettings.RENDER_HIT_COLOR_ARMOR) {
            int coords = getOverlay((T) entity, this.getAnimationCounter((T) entity, tickDelta));
            ((RenderedOverlay<T>) instance).oldanimations$render(matrixStack, vertexConsumerProvider, (T) entity, coords, light, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        } else {
            instance.render(matrixStack, vertexConsumerProvider, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        }
    }
}
