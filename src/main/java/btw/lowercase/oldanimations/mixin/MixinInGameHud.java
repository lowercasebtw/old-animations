package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGameHud.class, priority = Integer.MAX_VALUE)
public abstract class MixinInGameHud {
    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void renderStatusEffectOverlay$old(CallbackInfo ci) {
        if (!OldAnimations.CONFIG.legacySettings.VANILLA_STATUS_HUD)
            ci.cancel();
    }
}
