package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "visual_settings")
public class VisualSettingsConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_SWING_ANIMATION = false; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_SNEAK_ANIMATION = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean SMOOTH_SNEAK_ANIMATION = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_CAMERA_POSITION = false; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean PARALLAX_FIX = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_BACKWARDS_WALKING = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_DAMAGE_TILT = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean NO_DAMAGE_TILT = false;
}
