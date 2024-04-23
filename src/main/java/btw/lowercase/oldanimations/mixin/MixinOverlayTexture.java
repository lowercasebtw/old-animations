package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.render.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = OverlayTexture.class, priority = Integer.MAX_VALUE)
public abstract class MixinOverlayTexture {
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = -1308622593))
    private int customHitColor$old(int value) {
        /*
         * TODO: Reload OverlayTexture somehow to make the color change when config is
         * changed without having to restart the damn game!
         */
        return OldAnimations.CONFIG.combatSettings.HIT_COLOR;
    }
}
