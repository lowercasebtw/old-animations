package btw.lowercase.oldanimations.mixin.screen.hud;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.config.QOLConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(value = InGameHud.class, priority = Integer.MAX_VALUE)
public abstract class MixinInGameHud {
    @WrapOperation(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack renderHotbar$old$renderOffHandSlot(PlayerEntity instance, Operation<ItemStack> original) {
        return !OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND_SLOT ? ItemStack.EMPTY : original.call(instance);
    }

    // TODO: Option to render crosshair in thirdperson

//    @WrapOperation(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
//    private void renderCrosshair$old$customRendering(DrawContext instance, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, int width, int height, Operation<Void> original) {
//        QOLConfig.CrosshairRenderType crosshairRenderType = OldAnimations.CONFIG.qolSettings.CROSSHAIR_RENDER_TYPE;
//
//        boolean isCustomColor = crosshairRenderType == QOLConfig.CrosshairRenderType.CUSTOM_COLOR;
//        boolean shouldNotBlend = crosshairRenderType == QOLConfig.CrosshairRenderType.NO_BLEND || crosshairRenderType == QOLConfig.CrosshairRenderType.NO_BLEND_ALL || isCustomColor;
//
//        if (shouldNotBlend) {
//            RenderSystem.disableBlend();
//        }
//
//        if (isCustomColor) {
//            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
//            Vector3f crosshairColor = ColorHelper.toVector(OldAnimations.CONFIG.qolSettings.CROSSHAIR_COLOR);
//            RenderSystem.setShaderColor(crosshairColor.x, crosshairColor.y, crosshairColor.z, 255 /* TODO: Crosshair Transparency from color */);
//        }
//
//        original.call(instance, renderLayers, sprite, x, y, width, height);
//
//        if (shouldNotBlend) {
//            RenderSystem.enableBlend();
//        }
//    }
}
