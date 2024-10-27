package btw.lowercase.oldanimations;

import btw.lowercase.oldanimations.config.AnimationsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class OldAnimations implements ClientModInitializer {
    public static final AnimationsConfig CONFIG = AutoConfig.register(AnimationsConfig.class,
            PartitioningSerializer.wrap(JanksonConfigSerializer::new)).getConfig();

    public static int armorOverlayCoords;
    public static int previousHitColor = 0;

    @Override
    public void onInitializeClient() {
        previousHitColor = CONFIG.qolSettings.HIT_COLOR;
    }
}