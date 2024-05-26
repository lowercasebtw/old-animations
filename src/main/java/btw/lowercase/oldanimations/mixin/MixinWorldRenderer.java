package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinWorldRenderer {
    // TODO: Iris support

    // TODO: Fix FSB effecting the sky color

    @Shadow @Final private MinecraftClient client;

    @Shadow @Nullable private ClientWorld world;

    @Shadow @Nullable private VertexBuffer darkSkyBuffer;

    @Redirect(method = "drawEntityOutlinesFramebuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;canDrawEntityOutlines()Z"))
    public boolean drawEntityOutlinesFramebuffer$old$glowingOutline(WorldRenderer instance) {
        return OldAnimations.CONFIG.visualSettings.RENDER_GLOWING_EFFECT;
    }

    @Unique
    public double getHorizonHeight(ClientWorld world) {
        // mojang, why can't you just let me have access to flatWorld variable bruh
        boolean isFlatWorld = world.getLevelProperties().getHorizonShadingRatio() == 1.0F;
        return isFlatWorld ? (OldAnimations.CONFIG.legacySettings.OLD_HORIZON_HEIGHT ? 0.0 : world.getBottomY()) : 63.0;
    }

    @Inject(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/VertexBuffer;bind()V", ordinal = 0))
    private void renderSky$old$storeRequiredVariables(CallbackInfo ci, @Local MatrixStack matrixStack, @Local Vec3d vec3d, @Local ShaderProgram shaderProgram, @Share("matrixStack") LocalRef<MatrixStack> matrixStackRef, @Share("skyVec") LocalRef<Vec3d> vecRef, @Share("shaderProgram") LocalRef<ShaderProgram> programRef) {
        if (!OldAnimations.CONFIG.legacySettings.OLD_SKY_RENDERER)
            return;
        matrixStackRef.set(matrixStack);
        vecRef.set(vec3d);
        programRef.set(shaderProgram);
    }

    @Inject(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", shift = At.Shift.BEFORE, ordinal = 6))
    private void renderSky$old$deepBlueSkyHalf(CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) Matrix4f matrix4f, @Local(ordinal = 1, argsOnly = true) Matrix4f matrix4f2, @Local(argsOnly = true) float f, @Share("matrixStack") LocalRef<MatrixStack> matrixStackRef, @Share("skyVec") LocalRef<Vec3d> vecRef, @Share("shaderProgram") LocalRef<ShaderProgram> shaderProgramLocalRef) {
        if (!OldAnimations.CONFIG.legacySettings.OLD_SKY_RENDERER)
            return;

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

        MatrixStack matrixStack = matrixStackRef.get();

        assert this.client.player != null;
        // TODO: get variable from earlier in method, instead of recalling this for a 2nd time
        double d = this.client.player.getCameraPosVec(f).y - getHorizonHeight(this.world);
        // If Statement is a fix for it showing below y 0/-64
        // Supposedly/(not entirely) accurate functionality
        if (d < 0.0)
            return;

        matrixStack.push();
        // Changes color based on camera/player Y level
        matrixStack.translate(0.0F, -((float) (d - 16.0)), 0.0F);
        assert this.darkSkyBuffer != null;
        this.darkSkyBuffer.bind();
        this.darkSkyBuffer.draw(matrixStack.peek().getPositionMatrix(), matrix4f2, shaderProgramLocalRef.get());
        VertexBuffer.unbind();
        matrixStack.pop();
    }

    // TODO: SODIUM/EMBEDDIUM PROPER SUPPORT
    @Redirect(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DimensionEffects;getCloudsHeight()F"))
    public float renderClouds$old$cloudHeight(DimensionEffects instance) {
        if (!OldAnimations.CONFIG.legacySettings.OLD_CLOUD_HEIGHT)
            return instance.getCloudsHeight();
        return instance.getSkyType() == DimensionEffects.SkyType.END ? 8.0F : 128.0F;
    }
}