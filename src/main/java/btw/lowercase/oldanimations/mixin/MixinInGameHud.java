package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGameHud.class, priority = Integer.MAX_VALUE)
public abstract class MixinInGameHud {
    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void renderStatusEffectOverlay$old(CallbackInfo ci) {
        if (!OldAnimations.CONFIG.legacySettings.VANILLA_STATUS_HUD)
            ci.cancel();
    }

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack renderHotbar$old$renderOffHandSlot(PlayerEntity instance) {
        return !OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND_SLOT ? new ItemStack(Items.AIR) : instance.getOffHandStack();
    }
}
