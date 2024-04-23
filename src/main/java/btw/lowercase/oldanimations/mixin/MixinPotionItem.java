package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PotionItem.class, priority = Integer.MAX_VALUE)
public abstract class MixinPotionItem extends Item {
    public MixinPotionItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack itemStack) {
        return OldAnimations.CONFIG.legacySettings.POTION_GLINT || super.hasGlint(itemStack);
    }
}
