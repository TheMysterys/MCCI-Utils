package com.themysterys.mcciutils;

import com.themysterys.mcciutils.util.config.ModConfig;
import com.themysterys.mcciutils.util.discord.DiscordRP;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class McciUtils implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("MCCI Utils");
    public static ServerInfo mcci = new ServerInfo("MCC Island", "mccisland.net", false);
    public static boolean hasInitialized = false;
    public static String modVersion = String.valueOf(FabricLoader.getInstance().getModContainer("mcciutils").get().getMetadata().getVersion());


    @Override
    public void onInitialize() {
        //Registers the config
        ModConfig.init();

        //Start Discord IPC.
        DiscordRP.startRP();

        //Set Resource Pack Policy for MCC Island to Enabled as MCC Island requires a resource pack.
        mcci.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);

        hasInitialized = true;
        LOGGER.info("MCCI Utils has loaded!");
    }

    public static boolean isOnMCCI() {
        if(MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            String currentServerAddress = MinecraftClient.getInstance().getCurrentServerEntry().address;
            return currentServerAddress.endsWith("mccisland.net");
        }else {
            return  false;
        }
    }
}
