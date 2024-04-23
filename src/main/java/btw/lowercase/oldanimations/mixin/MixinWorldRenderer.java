package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
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

    @Shadow @Nullable private VertexBuffer starsBuffer;

    @Shadow @Nullable private VertexBuffer lightSkyBuffer;

    @Shadow @Final private static Identifier SUN;

    @Shadow @Final private static Identifier MOON_PHASES;

    @Shadow protected abstract void renderEndSky(MatrixStack matrixStack);

    @Shadow protected abstract boolean hasBlindnessOrDarkness(Camera camera);

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"), cancellable = true)
    public void renderSky$old(MatrixStack matrixStack, Matrix4f matrix4f, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        ci.cancel();
        runnable.run();

        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        if (bl || !(cameraSubmersionType != CameraSubmersionType.POWDER_SNOW && cameraSubmersionType != CameraSubmersionType.LAVA && !this.hasBlindnessOrDarkness(camera)))
            return;

        assert this.world != null;
        DimensionEffects.SkyType skyType = this.world.getDimensionEffects().getSkyType();
        if (skyType == DimensionEffects.SkyType.END) {
            this.renderEndSky(matrixStack);
            return;
        }

        if (skyType != DimensionEffects.SkyType.NORMAL)
            return;

        Vec3d skyColor = this.world.getSkyColor(this.client.gameRenderer.getCamera().getPos(), tickDelta);
        float skyColorR = (float) skyColor.x;
        float skyColorG = (float) skyColor.y;
        float skyColorB = (float) skyColor.z;

        BackgroundRenderer.applyFogColor();

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(skyColorR, skyColorG, skyColorB, 1.0F);
        ShaderProgram shaderProgram = RenderSystem.getShader();

        assert this.lightSkyBuffer != null;
        this.lightSkyBuffer.bind();
        this.lightSkyBuffer.draw(matrixStack.peek().getPositionMatrix(), matrix4f, shaderProgram);
        VertexBuffer.unbind();

        RenderSystem.enableBlend();

        float[] fs = this.world.getDimensionEffects().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
        float j, l, p, q, r;
        if (fs != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            matrixStack.push();

            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            j = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(j));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));

            float k = fs[0];
            l = fs[1];
            float m = fs[2];

            Matrix4f matrix4f2 = matrixStack.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(matrix4f2, 0.0F, 100.0F, 0.0F).color(k, l, m, fs[3]).next();

            for (int o = 0; o <= 16; ++o) {
                p = (float) o * 6.2831855F / 16.0F;
                q = MathHelper.sin(p);
                r = MathHelper.cos(p);
                bufferBuilder.vertex(matrix4f2, q * 120.0F, r * 120.0F, -r * 40.0F * fs[3]).color(fs[0], fs[1], fs[2], 0.0F).next();
            }

            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            matrixStack.pop();
        }

        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        matrixStack.push();

        {
            /* Weather */
            j = 1.0F - this.world.getRainGradient(tickDelta);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, j);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.world.getSkyAngle(tickDelta) * 360.0F));
        }

        Matrix4f matrix4f3 = matrixStack.peek().getPositionMatrix();

        {
            /* Sun */
            l = 30.0F;
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, SUN);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f3, -l, 100.0F, -l).texture(0.0F, 0.0F).next();
            bufferBuilder.vertex(matrix4f3, l, 100.0F, -l).texture(1.0F, 0.0F).next();
            bufferBuilder.vertex(matrix4f3, l, 100.0F, l).texture(1.0F, 1.0F).next();
            bufferBuilder.vertex(matrix4f3, -l, 100.0F, l).texture(0.0F, 1.0F).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }

        {
            /* Moon */
            l = 20.0F;
            RenderSystem.setShaderTexture(0, MOON_PHASES);
            int s = this.world.getMoonPhase();
            int t = s % 4;
            int n = s / 4 % 2;
            float u = ((float) t) / 4.0F;
            p = ((float) n) / 2.0F;
            q = (float) (t + 1) / 4.0F;
            r = (float) (n + 1) / 2.0F;
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f3, -l, -100.0F, l).texture(q, r).next();
            bufferBuilder.vertex(matrix4f3, l, -100.0F, l).texture(u, r).next();
            bufferBuilder.vertex(matrix4f3, l, -100.0F, -l).texture(u, p).next();
            bufferBuilder.vertex(matrix4f3, -l, -100.0F, -l).texture(q, p).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }

        {
            /* Stars */
            float v = this.world.method_23787(tickDelta) * j; // getStarBrightness
            if (v > 0.0F) {
                RenderSystem.setShaderColor(v, v, v, v);
                BackgroundRenderer.clearFog();
                assert this.starsBuffer != null;
                this.starsBuffer.bind();
                this.starsBuffer.draw(matrixStack.peek().getPositionMatrix(), matrix4f, GameRenderer.getPositionProgram());
                VertexBuffer.unbind();
                runnable.run();
            }
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        matrixStack.pop();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);

        assert this.client.player != null;
        double d = this.client.player.getCameraPosVec(tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
        assert this.darkSkyBuffer != null;
        if (d < 0.0) {
            matrixStack.push();
            matrixStack.translate(0.0F, 12.0F, 0.0F);
            this.darkSkyBuffer.bind();
            this.darkSkyBuffer.draw(matrixStack.peek().getPositionMatrix(), matrix4f, shaderProgram);
            VertexBuffer.unbind();
            matrixStack.pop();
        }

        if (OldAnimations.CONFIG.legacySettings.OLD_SKY_RENDERER) {
            /* THE DEEP BLUE BOTTOM OF THE SKY */
            if (this.world.getDimensionEffects().isAlternateSkyColor()) {
                RenderSystem.setShaderColor(skyColorR * 0.2F + 0.04F, skyColorG * 0.2F + 0.04F, skyColorB * 0.6F + 0.1F, 1.0F);
            } else {
                RenderSystem.setShaderColor(skyColorR, skyColorG, skyColorB, 1.0F);
            }

            // If Statement is a fix for it showing below y 0/-64
            // Not entirely accurate functionality
            if (d > 0.0) {
                matrixStack.push();
                // Changes color based on camera/player Y level
                matrixStack.translate(0.0F, -((float) (d - 16.0)), 0.0F);
                this.darkSkyBuffer.bind();
                this.darkSkyBuffer.draw(matrixStack.peek().getPositionMatrix(), matrix4f, shaderProgram);
                VertexBuffer.unbind();
                matrixStack.pop();
            }
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
    }
}