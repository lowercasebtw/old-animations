package btw.lowercase.oldanimations.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "legacy_settings")
public class LegacySettingsConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_POTION_COLORS = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean POTION_GLINT = false; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_ENCHANT_GLINT = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean ENCHANT_GLINT_1_7 = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_SKY_RENDERER = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_FOG = false;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean DISABLE_COMBAT_SOUNDS = true;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean VANILLA_STATUS_HUD = true; // Complete

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean RENDER_OFFHAND = true;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean RENDER_OFFHAND_SLOT = true;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean OLD_INVENTORY_LAYOUT = true;
}
