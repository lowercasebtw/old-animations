package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import btw.lowercase.oldanimations.config.QOLConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGameHud.class, priority = Integer.MAX_VALUE)
public abstract class MixinInGameHud {
    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void renderStatusEffectOverlay$old(CallbackInfo ci) {
        if (!OldAnimations.CONFIG.legacySettings.VANILLA_STATUS_HUD)
            ci.cancel();
    }

    // TODO: FIX Support with Sword Blocking Mod
    // @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    // public ItemStack renderHotbar$old$renderOffHandSlot(PlayerEntity instance) {
    //     return !OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND_SLOT ? ItemStack.EMPTY : instance.getOffHandStack();
    // }

    @Inject(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0, shift = At.Shift.BEFORE))
    public void renderCrosshair$old$blend_before(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        QOLConfig.CrosshairRenderType crosshairRenderType = OldAnimations.CONFIG.qolSettings.CROSSHAIR_RENDER_TYPE;
        boolean isCustomColor = crosshairRenderType == QOLConfig.CrosshairRenderType.CUSTOM_COLOR;
        if (crosshairRenderType == QOLConfig.CrosshairRenderType.NO_BLEND || crosshairRenderType == QOLConfig.CrosshairRenderType.NO_BLEND_ALL || isCustomColor) {
            RenderSystem.disableBlend();
            if (isCustomColor) {
                // Color color = new Color(OldAnimations.CONFIG.qolSettings.CROSSHAIR_COLOR, true);
                // TODO
            }
        }
    }

    @Inject(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0, shift = At.Shift.AFTER))
    public void renderCrosshair$old$blend_after(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        QOLConfig.CrosshairRenderType crosshairRenderType = OldAnimations.CONFIG.qolSettings.CROSSHAIR_RENDER_TYPE;
        if (crosshairRenderType == QOLConfig.CrosshairRenderType.NO_BLEND || crosshairRenderType == QOLConfig.CrosshairRenderType.NO_BLEND_ALL || crosshairRenderType == QOLConfig.CrosshairRenderType.CUSTOM_COLOR) {
            RenderSystem.enableBlend();
        }
    }

    // TODO: NO_BLEND_ALL, CUSTOM_COLOR
}
