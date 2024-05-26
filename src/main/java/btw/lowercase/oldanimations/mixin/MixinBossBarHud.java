package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BossBarHud.class, priority = Integer.MAX_VALUE)
public class MixinBossBarHud {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render$qol$disableBossbar(DrawContext context, CallbackInfo ci) {
        if (!OldAnimations.CONFIG.qolSettings.RENDER_BOSSBAR)
            ci.cancel();
    }
}
