package btw.lowercase.oldanimations.mixin.screen;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = InventoryScreen.class, priority = Integer.MAX_VALUE)
public class MixinInventoryScreen {
    // NOTE: This only moves the title of the crafting area
    // For the slots moving, look in MixinPlayerScreenHandler

    @ModifyArg(method = "drawForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I"), index = 2)
    private int drawForeground$old$titleX(int original) {
        if (OldAnimations.CONFIG.legacySettings.OLD_INVENTORY_LAYOUT) {
            return 86;
        } else {
            return original;
        }
    }

    @ModifyArg(method = "drawForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I"), index = 3)
    private int drawForeground$old$titleY(int original) {
        if (OldAnimations.CONFIG.legacySettings.OLD_INVENTORY_LAYOUT) {
            return 16;
        } else {
            return original;
        }
    }
}
