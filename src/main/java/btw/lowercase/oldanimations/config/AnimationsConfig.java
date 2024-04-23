package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "old_animations")
public class AnimationsConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("visual_settings")
    @ConfigEntry.Gui.TransitiveObject
    public VisualSettingsConfig visualSettings = new VisualSettingsConfig();

    @ConfigEntry.Category("legacy_settings")
    @ConfigEntry.Gui.TransitiveObject
    public LegacySettingsConfig legacySettings = new LegacySettingsConfig();

    @ConfigEntry.Category("block_settings")
    @ConfigEntry.Gui.TransitiveObject
    public BlockSettingsConfig blockSettings = new BlockSettingsConfig();

    @ConfigEntry.Category("combat_settings")
    @ConfigEntry.Gui.TransitiveObject
    public CombatSettingsConfig combatSettings = new CombatSettingsConfig();
}