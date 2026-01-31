package leriz.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "nohook")
public class NohookConfig implements ConfigData {
    public boolean enabled = true;
    public boolean onlyPlayerHooks = true;
    public boolean onlyWhenHooked = true;
    public boolean thirdPersonSupport = true;
    public boolean hideLine = false;
}
