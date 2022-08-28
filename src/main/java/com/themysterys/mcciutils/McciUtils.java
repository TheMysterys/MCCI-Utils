package com.themysterys.mcciutils;

import com.themysterys.mcciutils.util.config.ModConfig;
import com.themysterys.mcciutils.util.discord.DiscordRP;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

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

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("goat").executes(ctx -> {
            ctx.getSource().getPlayer().playSound(SoundEvents.ENTITY_GOAT_SCREAMING_AMBIENT, SoundCategory.MASTER, 1F, 1F);
            ctx.getSource().sendFeedback(Text.literal("Gray Goats!").formatted(Formatting.GRAY));
            return 1;
        })));

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
