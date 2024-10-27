package btw.lowercase.oldanimations.mixin.screen.hud;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.UUID;

@Mixin(value = BossBarHud.class, priority = Integer.MAX_VALUE)
public class MixinBossBarHud {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/Map;isEmpty()Z"))
    public boolean render$qol$disableBossbar(Map<UUID, ClientBossBar> instance) {
        if (!OldAnimations.CONFIG.qolSettings.RENDER_BOSSBAR)
            return true;
        return instance.isEmpty();
    }
}
