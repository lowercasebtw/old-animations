package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "qol")
public class QOLConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
    @ConfigEntry.Gui.Tooltip(count = 1)
    public int BLOCK_OUTLINE_WIDTH = 1;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean USE_BLOCK_OUTLINE = true;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    @ConfigEntry.Gui.Tooltip(count = 1)
    public int BLOCK_OUTLINE_COLOR = 0xFFFFFFFF;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean USE_BLOCK_OVERLAY = false;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    @ConfigEntry.Gui.Tooltip(count = 1)
    public int BLOCK_OVERLAY_COLOR = 0xFFFFFFFF;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean ALWAYS_SHARP_PARTICLES = false; // Complete

    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @ConfigEntry.Gui.Tooltip(count = 1)
    public int PARTICLE_MULTIPLIER = 1;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    @ConfigEntry.Gui.Tooltip(count = 2)
    public int HIT_COLOR = 0xB20000FF; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean RENDER_HIT_COLOR_ARMOR = false; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean USE_1_7_HIT_RENDERER = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean MINIMAL_VIEW_BOBBING = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean SHOW_NAMETAG_THIRD_PERSON = false; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean RENDER_BOSSBAR = true; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean RENDER_FIRST_PERSON_PARTICLES = true;
}
