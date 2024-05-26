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
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class OldAnimations implements ClientModInitializer {
	public static final AnimationsConfig CONFIG = AutoConfig.register(AnimationsConfig.class,
			PartitioningSerializer.wrap(JanksonConfigSerializer::new)).getConfig();

	public static int previousHitColor = 0;

	@Override
	public void onInitializeClient() {
		previousHitColor = CONFIG.qolSettings.HIT_COLOR;
	}
}