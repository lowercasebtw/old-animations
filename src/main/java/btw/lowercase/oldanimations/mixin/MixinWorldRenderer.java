package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinWorldRenderer {
    @Shadow @Final private MinecraftClient client;

    @Shadow @Nullable private ClientWorld world;

    @Shadow @Nullable private VertexBuffer darkSkyBuffer;

    @Inject(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/VertexBuffer;bind()V", ordinal = 0))
    private void renderSky$old$storeShader(CallbackInfo ci, @Local MatrixStack matrixStack, @Local Vec3d vec3d, @Local ShaderProgram shaderProgram, @Share("matrixStack") LocalRef<MatrixStack> matrixStackRef, @Share("skyVec") LocalRef<Vec3d> vecRef, @Share("shaderProgram") LocalRef<ShaderProgram> programRef) {
        matrixStackRef.set(matrixStack);
        vecRef.set(vec3d);
        programRef.set(shaderProgram);
    }

    @Inject(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 6))
    private void renderSky$old$deepBlue(CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) Matrix4f matrix4f, @Local(ordinal = 1, argsOnly = true) Matrix4f matrix4f2, @Local(argsOnly = true) float f, @Share("matrixStack") LocalRef<MatrixStack> matrixStackRef, @Share("skyVec") LocalRef<Vec3d> vecRef, @Share("shaderProgram") LocalRef<ShaderProgram> shaderProgramLocalRef) {
        if (!OldAnimations.CONFIG.legacySettings.OLD_SKY_RENDERER) return;

        MatrixStack matrixStack = matrixStackRef.get();

        Vec3d skyVec = vecRef.get();
        float skyColorR = (float) skyVec.x;
        float skyColorG = (float) skyVec.y;
        float skyColorB = (float) skyVec.z;

        assert this.world != null;
        if (this.world.getDimensionEffects().isAlternateSkyColor()) {
            RenderSystem.setShaderColor(skyColorR * 0.2F + 0.04F, skyColorG * 0.2F + 0.04F, skyColorB * 0.6F + 0.1F, 1.0F);
        } else {
            RenderSystem.setShaderColor(skyColorR, skyColorG, skyColorB, 1.0F);
        }

        // If Statement is a fix for it showing below y 0/-64
        // Not entirely accurate functionality
        assert this.client.player != null;
        double d = this.client.player.getCameraPosVec(f).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
        if (d > 0.0) {
            matrixStack.push();
            // Changes color based on camera/player Y level
            matrixStack.translate(0.0F, -((float) (d - 16.0)), 0.0F);
            assert this.darkSkyBuffer != null;
            this.darkSkyBuffer.bind();
            this.darkSkyBuffer.draw(matrixStack.peek().getPositionMatrix(), matrix4f2, shaderProgramLocalRef.get());
            VertexBuffer.unbind();
            matrixStack.pop();
        }
    }
}