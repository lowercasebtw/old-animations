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

    @ConfigEntry.Category("qol")
    @ConfigEntry.Gui.TransitiveObject
    public QOLConfig qolSettings = new QOLConfig();

    @ConfigEntry.Category("bug_fixes")
    @ConfigEntry.Gui.TransitiveObject
    public BugFixesConfig bugFixes = new BugFixesConfig();
}