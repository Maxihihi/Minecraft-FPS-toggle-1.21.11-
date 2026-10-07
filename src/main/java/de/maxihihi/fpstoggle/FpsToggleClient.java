package de.maxihihi.fpstoggle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class FpsToggleClient implements ClientModInitializer {
    private static final int TOGGLE_FPS = 30;
    private static KeyBinding toggleKey;
    private static boolean limited;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fps-toggle.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                toggleFps(client);
            }
        });
    }

    private static void toggleFps(MinecraftClient client) {
        limited = !limited;
        client.options.getMaxFps().setValue(limited ? TOGGLE_FPS : 260);

        if (client.player != null) {
            client.player.sendMessage(
                    Text.literal(limited ? "FPS-Limit: 30" : "FPS-Limit: Unbegrenzt"),
                    false
            );
        }
    }
}