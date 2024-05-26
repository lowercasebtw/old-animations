package btw.lowercase.oldanimations;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

public interface RenderedOverlay<T> {
    // Code used from/inspired by:
    // Licensed CC0-1.0
    // https://modrinth.com/user/rizecookey
    // https://modrinth.com/mod/cookeymod
    // https://github.com/rizecookey/CookeyMod/tree/1.20.6/src/main
    // net/rizecookey/cookeymod/extension/OverlayRendered.java
    void oldanimations$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, T livingEntity, int coords, int light, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch);
}
