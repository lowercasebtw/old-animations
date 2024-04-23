package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "block_settings")
public class BlockSettingsConfig implements ConfigData {
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
}
