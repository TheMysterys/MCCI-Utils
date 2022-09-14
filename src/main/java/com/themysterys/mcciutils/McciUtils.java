package com.themysterys.mcciutils;

import com.themysterys.mcciutils.chat.StackedMessage;
import com.themysterys.mcciutils.commands.RegisterCommands;
import com.themysterys.mcciutils.util.config.ModConfig;
import com.themysterys.mcciutils.util.discord.DiscordCore;
import com.themysterys.mcciutils.util.websockets.WebsocketCore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class McciUtils implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("MCCI Utils");
    public static boolean hasInitialized = false;
    public static String modVersion = String.valueOf(FabricLoader.getInstance().getModContainer("mcciutils").get().getMetadata().getVersion());

    public static final Set<StackedMessage> COMPACTED_MESSAGES = new HashSet<>();

    public static boolean isOnMCCI() {
        if (MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            String currentServerAddress = MinecraftClient.getInstance().getCurrentServerEntry().address;
            return currentServerAddress.endsWith("mccisland.net");
        } else {
            return false;
        }
    }

    @Override
    public void onInitialize() {
        //Registers the config
        ModConfig.init();

        //Start Discord IPC.
        try {
            DiscordCore.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebsocketCore.init();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> RegisterCommands.register(dispatcher));

        hasInitialized = true;
        LOGGER.info("MCCI Utils has loaded!");
    }
}
