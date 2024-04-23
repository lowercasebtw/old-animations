package btw.lowercase.oldanimations.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CreativeInventoryScreen.CreativeScreenHandler.class, priority = Integer.MAX_VALUE)
public class MixinCreativeInventoryScreen {
}
