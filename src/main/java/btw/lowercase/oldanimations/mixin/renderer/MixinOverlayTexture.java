package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.render.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = OverlayTexture.class, priority = Integer.MAX_VALUE)
public abstract class MixinOverlayTexture {
    // OLD VALUE: 1308622593
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = -1291911168))
    private int init$old$customHitColor(int value) {
        return OldAnimations.CONFIG.qolSettings.HIT_COLOR;
    }
}
