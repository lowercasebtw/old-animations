package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.mixin.accessor.ClientWorldPropertiesAccessor;
import btw.lowercase.oldanimations.mixin.accessor.SkyRenderingAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ColorHelper;
import org.joml.Vector3f;
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
    // TODO: Fix FSB effecting the sky color?

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Nullable
    private ClientWorld world;

    @Shadow
    @Final
    private SkyRendering skyRendering;

    @Unique
    public double getHorizonHeight(ClientWorld world) {
        return ((ClientWorldPropertiesAccessor) world.getLevelProperties()).isFlatWorld() ? (OldAnimations.CONFIG.legacySettings.OLD_HORIZON_HEIGHT ? 0.0 : world.getBottomY()) : 63.0;
    }

    @Unique
    private void renderSkyBlueVoid(MatrixStack matrixStack, int skyColor, double depth) {
        // TODO/NOTE: If Statement is a fix for it showing below y 0/-64, supposedly/(not entirely) accurate functionality
        if (depth >= 0.0) {
            ShaderProgram shaderProgram = RenderSystem.setShader(ShaderProgramKeys.POSITION);

            assert this.world != null;
            Vector3f skyColorVec = ColorHelper.toVector(skyColor);
            if (this.world.getDimensionEffects().isAlternateSkyColor()) {
                RenderSystem.setShaderColor(skyColorVec.x * 0.2F + 0.04F, skyColorVec.y * 0.2F + 0.04F, skyColorVec.z * 0.6F + 0.1F, 1.0F);
            } else {
                RenderSystem.setShaderColor(skyColorVec.x, skyColorVec.y, skyColorVec.z, 1.0F);
            }

            matrixStack.push();
            matrixStack.multiplyPositionMatrix(RenderSystem.getModelViewMatrix());
            matrixStack.translate(0.0F, -((float) (depth - 16.0)), 0.0F);
            SkyRenderingAccessor skyRenderingAccessor = (SkyRenderingAccessor) this.skyRendering;
            skyRenderingAccessor.getDarkSkyBuffer().bind();
            skyRenderingAccessor.getDarkSkyBuffer().draw(matrixStack.peek().getPositionMatrix(), RenderSystem.getProjectionMatrix(), shaderProgram);
            VertexBuffer.unbind();
            matrixStack.pop();
        }
    }

    @Redirect(method = "drawEntityOutlinesFramebuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;canDrawEntityOutlines()Z"))
    private boolean drawEntityOutlinesFramebuffer$old$glowingOutline(WorldRenderer instance) {
        return OldAnimations.CONFIG.visualSettings.RENDER_GLOWING_EFFECT;
    }

    // TODO/NOTE: Possible injection spot could be wrong, will fix later on
    @Inject(method = "method_62215", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/SkyRendering;renderSky(FFF)V", shift = At.Shift.AFTER))
    private void renderSky$old$deepBlueSkyHalf(Fog fog, DimensionEffects.SkyType skyType, float tickDelta, DimensionEffects dimensionEffects, CallbackInfo ci, @Local MatrixStack matrixStack, @Local(ordinal = 2) int skyColor) {
        if (OldAnimations.CONFIG.legacySettings.OLD_SKY_RENDERER) {
            assert this.client.player != null;
            assert this.world != null;
            this.renderSkyBlueVoid(matrixStack, skyColor, this.client.player.getCameraPosVec(tickDelta).y - getHorizonHeight(this.world));
        }
    }

    // TODO: SODIUM/EMBEDDIUM PROPER SUPPORT
    // NOTE: Possibly WrapOperation on renderClouds?
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DimensionEffects;getCloudsHeight()F"))
    private float render$old$cloudHeight(DimensionEffects instance) {
        if (OldAnimations.CONFIG.legacySettings.OLD_CLOUD_HEIGHT) {
            return instance.getSkyType() == DimensionEffects.SkyType.END ? 8.0F : 128.0F;
        } else {
            return instance.getCloudsHeight();
        }
    }
}