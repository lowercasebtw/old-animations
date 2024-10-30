package btw.lowercase.oldanimations.mixin.screen.hud;

import btw.lowercase.oldanimations.OldAnimations;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = InGameHud.class, priority = Integer.MAX_VALUE)
public abstract class MixinInGameHud {
    @WrapOperation(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack renderHotbar$old$renderOffHandSlot(PlayerEntity instance, Operation<ItemStack> original) {
        if (!OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND_SLOT) {
            return ItemStack.EMPTY;
        } else {
            return original.call(instance);
        }
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
