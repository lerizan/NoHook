package leriz.client;

import leriz.Nohook;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class NohookClient implements ClientModInitializer {
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nohook.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.NoHook"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                Nohook.getConfig().enabled = !Nohook.getConfig().enabled;
                me.shedaniel.autoconfig.AutoConfig.getConfigHolder(leriz.config.NohookConfig.class).save();
                if (client.player != null) {
                    client.player.sendMessage(Text.translatable(
                            "message.nohook.toggle",
                            Nohook.getConfig().enabled ? Text.translatable("message.nohook.enabled") : Text.translatable("message.nohook.disabled")
                    ), true);
                }
            }
        });
    }
}
