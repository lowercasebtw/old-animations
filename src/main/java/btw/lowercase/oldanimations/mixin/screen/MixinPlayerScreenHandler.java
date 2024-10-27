package btw.lowercase.oldanimations.mixin.screen;

import net.minecraft.screen.PlayerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PlayerScreenHandler.class, priority = Integer.MAX_VALUE)
public class MixinPlayerScreenHandler {
    // NOTE: THIS IS THE SURVIVAL INVENTORY

//	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/CraftingResultSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/inventory/Inventory;III)V"), index = 4)
//	public int old$inventoryCraftingSlotsResult$x(int x) {
//		return x + (OldAnimations.CONFIG.legacySettings.OLD_INVENTORY_LAYOUT ? OLD_CRAFTING_OUTPUT_X_OFFSET : 0);
//	}
//
//	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/CraftingResultSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/inventory/Inventory;III)V"), index = 5)
//	public int old$inventoryCraftingSlotsResult$y(int y) {
//		return y + (OldAnimations.CONFIG.legacySettings.OLD_INVENTORY_LAYOUT ? OLD_CRAFTING_OUTPUT_Y_OFFSET : 0);
//	}
//
//	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V", ordinal = 0), index = 2)
//	public int old$inventoryCraftingSlots$x(int x) {
//		return x + (OldAnimations.CONFIG.legacySettings.OLD_INVENTORY_LAYOUT ? OLD_CRAFTING_X_OFFSET : 0);
//	}
//
//	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V", ordinal = 0), index = 3)
//	public int old$inventoryCraftingSlots$y(int y) {
//		return y + (OldAnimations.CONFIG.legacySettings.OLD_INVENTORY_LAYOUT ? OLD_CRAFTING_Y_OFFSET : 0);
//	}
}
