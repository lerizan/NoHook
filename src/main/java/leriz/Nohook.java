package leriz;

import leriz.config.NohookConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class Nohook implements ModInitializer {
    private static NohookConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(NohookConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(NohookConfig.class).getConfig();
    }

    public static NohookConfig getConfig() {
        return config;
    }
}
