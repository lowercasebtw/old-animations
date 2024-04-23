package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "combat_settings")
public class CombatSettingsConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean ALWAYS_SHARP_PARTICLES = false; // Complete

    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @ConfigEntry.Gui.Tooltip(count = 1)
    public int PARTICLE_MULTIPLIER = 1;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    @ConfigEntry.Gui.Tooltip(count = 2)
    public int HIT_COLOR = 0xB20000FF; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean RENDER_HIT_COLOR_ARMOR = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean USE_1_7_HIT_RENDERER = false;
}
