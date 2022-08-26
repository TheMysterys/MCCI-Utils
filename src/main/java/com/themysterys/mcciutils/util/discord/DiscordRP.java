package com.themysterys.mcciutils.util.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.MinecraftClient;

import java.time.OffsetDateTime;


public class DiscordRP {
    static final IPCClient client = new IPCClient(1012500697880731708L);
    static RichPresence.Builder builder = new RichPresence.Builder();
    public static boolean hasRPStarted = false;
    public static boolean isRPRunning = false;
    public static int discordRPErrorcode = 0;
    //0 = Normal State, 1 = Error, 2 = MacOS

    public static void startRP() {
        if (!MinecraftClient.IS_SYSTEM_MAC) {
            try {
                setup();
            } catch (Exception e) {
                McciUtils.LOGGER.error("Failed to start rich presence, Your Device/Install may not support rich presence!");
                e.printStackTrace();
                discordRPErrorcode = 1;
            }
        } else {
            McciUtils.LOGGER.error("Rich Presence doesn't support macOS.");
            discordRPErrorcode = 2;
        }
    }

    public static void updateStatus(String state, String details) {
        if (!isRPRunning && ModConfig.INSTANCE.enableDiscordStatus) {
            setup();
        }
        if(discordRPErrorcode == 0) {
            if (isRPRunning && !ModConfig.INSTANCE.enableDiscordStatus) {
                closeRP();
            } else {
                try {
                    builder.setState(state)
                            .setDetails(details)
                            .setStartTimestamp(OffsetDateTime.now())
                            .setLargeImage("logo", "play.mccisland.net")
                            .addButton("Get MCCI Utils", "https://modrinth.com/mod/mcci-utils");
                    client.sendRichPresence(builder.build());
                } catch (IllegalStateException e) {
                    McciUtils.LOGGER.error("IPC not connected! Attempting to reconnect IPC");
                    connectClient();
                }
            }
        }
    }

    public static void closeRP() {
        if(isRPRunning) {
            client.close();
            isRPRunning = false;
            McciUtils.LOGGER.info("MCCI Rich Presence has been closed!");
        }
    }

    private static void setup(){
        DiscordRP.client.setListener(new IPCListener(){
            @Override
            public void onReady(IPCClient client)
            {
                hasRPStarted = true;
                isRPRunning = true;
                McciUtils.LOGGER.info("MCCI Rich presence Ready!");
            }
        });
        connectClient();
    }

    public static void connectClient(){
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            McciUtils.LOGGER.error("Unable To Connect To Discord Client");
            e.printStackTrace();
        }
    }

    public static class Defaults{
        //This nested class stores all of the default items for Rich Presence

        public static String defaultDetails(){
            return switch (ModConfig.INSTANCE.customDetails) {
                case IP -> "IP: play.mccisland.net";
                case USERNAME -> "Playing as " + MinecraftClient.getInstance().getSession().getUsername();
                case MODVERSION -> "Using Version v" + McciUtils.modVersion;
            };
        }
    }
}
