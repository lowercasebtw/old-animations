package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, priority = Integer.MAX_VALUE)
public abstract class MixinItem {
    @Inject(method = "hasGlint", at = @At("RETURN"), cancellable = true)
    public void hasGlint$old$potionGlint(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!OldAnimations.CONFIG.legacySettings.POTION_GLINT || !(stack.getItem() instanceof PotionItem))
            return;
        boolean hasGlintOverrideComponent = stack.getComponents().contains(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE);
        if (hasGlintOverrideComponent)
            return;
        PotionContentsComponent potionComponent = stack.getComponents().get(DataComponentTypes.POTION_CONTENTS);
        if (potionComponent == null)
            return;
        cir.setReturnValue(potionComponent.hasEffects());
    }
}
