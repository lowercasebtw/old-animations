package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.IRenderedOverlay;
import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;

@Mixin(value = ArmorFeatureRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinArmorFeatureRenderer<T extends BipedEntityRenderState, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> implements IRenderedOverlay<T> {
    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    // TODO: Fix armor damage color
    // Code used from/inspired by:
    // Licensed CC0-1.0
    // https://modrinth.com/user/rizecookey
    // https://modrinth.com/mod/cookeymod
    // https://github.com/rizecookey/CookeyMod/tree/1.20.6/src/main
    // net/rizecookey/cookeymod/mixin/client/HumanoidArmorLayerMixin.java
    @Override
    public void oldanimations$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, T entityRenderState, int coords, int light, float yaw, float pitch) {
        OldAnimations.armorOverlayCoords = coords;
        this.render(matrixStack, vertexConsumerProvider, light, entityRenderState, yaw, pitch);
    }
}
