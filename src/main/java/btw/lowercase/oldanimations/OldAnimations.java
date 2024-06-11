package btw.lowercase.oldanimations;

import btw.lowercase.oldanimations.config.AnimationsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class OldAnimations implements ClientModInitializer {
	public static final AnimationsConfig CONFIG = AutoConfig.register(AnimationsConfig.class,
			PartitioningSerializer.wrap(JanksonConfigSerializer::new)).getConfig();

	public static int previousHitColor = 0;

	public static boolean oldInventoryLayout = false;

	@Override
	public void onInitializeClient() {
		previousHitColor = CONFIG.qolSettings.HIT_COLOR;
		oldInventoryLayout = !CONFIG.legacySettings.OLD_INVENTORY_LAYOUT;
	}

	private static int OLD_CRAFTING_X_OFFSET = 10;
	private static int OLD_CRAFTING_Y_OFFSET = -8;
	private static int OLD_CRAFTING_OUTPUT_X_OFFSET = -OLD_CRAFTING_X_OFFSET;
	private static int OLD_CRAFTING_OUTPUT_Y_OFFSET = OLD_CRAFTING_Y_OFFSET + 7;

	public static void updateInventorySlots() {
//		MinecraftClient client = MinecraftClient.getInstance();
//		if (client.player != null) {
//			Inventory inventory = client.player.getInventory();
//			for (int i = 0; i < client.player.playerScreenHandler.slots.size(); ++i) {
//				Slot slot = client.player.playerScreenHandler.slots.get(i);
//				int index = slot.getIndex();
//				if (index == 0) {
//					client.player.playerScreenHandler.slots.set(i, new CraftingResultSlot(client.player, client.player.playerScreenHandler.craftingInput, client.player.playerScreenHandler.craftingResult, 0, slot.x + (oldInventoryLayout ? OLD_CRAFTING_OUTPUT_X_OFFSET : 0), slot.y + (oldInventoryLayout ? OLD_CRAFTING_OUTPUT_Y_OFFSET : 0)));
//				}
////				if (index > 1 && index <= 3) {
////					client.player.playerScreenHandler.slots.set(slot.id, new Slot(inventory, index, slot.x + (oldInventoryLayout ? OLD_CRAFTING_X_OFFSET : 0), slot.y + (oldInventoryLayout ? OLD_CRAFTING_Y_OFFSET : 0)));
////				}
//			}
//		}
	}
}