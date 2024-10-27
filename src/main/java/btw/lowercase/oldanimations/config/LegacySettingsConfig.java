package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "legacy_settings")
public class LegacySettingsConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean OLD_BACKWARDS_WALKING = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_DAMAGE_TILT = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_BUTTON_TEXT_COLOR = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean BLEND_PARTICLE_COLORS = false;

    @ConfigEntry.Gui.Tooltip
    public boolean SWORD_BLOCKING = false;

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_ENCHANT_GLINT = false;

    @ConfigEntry.Gui.Tooltip
    public boolean ENCHANT_GLINT_1_7 = false;

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_SKY_RENDERER = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_FOG = false;

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_CLOUD_HEIGHT = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_HORIZON_HEIGHT = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean DISABLE_COMBAT_SOUNDS = false; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean RENDER_OFFHAND = true; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean RENDER_OFFHAND_SLOT = true; // Complete

    @ConfigEntry.Gui.Tooltip
    public boolean OLD_INVENTORY_LAYOUT = true; // TODO: slots
}
