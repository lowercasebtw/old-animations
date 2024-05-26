package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.RenderedOverlay;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = ArmorFeatureRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> implements RenderedOverlay<T> {
    @Shadow public abstract void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l);

    @Unique
    private int coords;

    @ModifyArg(method = "renderArmorParts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"), index = 3)
    public int modifyOverlayCoords(int original) {
        return OldAnimations.CONFIG.qolSettings.RENDER_HIT_COLOR_ARMOR ? coords : original;
    }

    // Code used from/inspired by:
    // Licensed CC0-1.0
    // https://modrinth.com/user/rizecookey
    // https://modrinth.com/mod/cookeymod
    // https://github.com/rizecookey/CookeyMod/tree/1.20.6/src/main
    // net/rizecookey/cookeymod/mixin/client/HumanoidArmorLayerMixin.java
    @Override
    public void oldanimations$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, T livingEntity, int coords, int light, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        this.coords = coords;
        this.render(matrixStack, vertexConsumerProvider, light, livingEntity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
    }
}
