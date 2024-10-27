package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "visual_settings")
public class VisualSettingsConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean OLD_SWING_ANIMATION = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_SNEAK_ANIMATION = false; // Complete

    // NOTE: This could be considered cheating, proceed with caution!
    // NOTE: Also this should be moved somewhere else, idk where to put it otherwise currently
    @ConfigEntry.Gui.Tooltip
    public boolean OLD_SNEAK_MECHANIC = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean SMOOTH_SNEAK_ANIMATION = true; // Complete

    @ConfigEntry.Gui.Tooltip
    public CameraVersion CAMERA_VERSION = CameraVersion.LATEST; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean NO_DAMAGE_TILT = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean RENDER_GLOWING_EFFECT = true; // Complete

    public enum CameraVersion {
        _BELOW_OR_1_8,
        _1_9_to_1_13_2,
        _1_14_to_1_14_3,
        _1_14_4_to_1_20,
        BEDROCK,
        LATEST
    }
}
