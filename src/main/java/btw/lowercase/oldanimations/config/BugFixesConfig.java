package btw.lowercase.oldanimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "bug_fixes")
public class BugFixesConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean VERTICAL_BOBBING_TILT = false; // MC-225335
}
