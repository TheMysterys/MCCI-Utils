package com.themysterys.mcciutils.util.discord;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.config.ModConfig;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.activity.Activity;
import net.minecraft.client.MinecraftClient;

import java.time.Instant;


public class DiscordRP {

    public static Core discordRPC;
    public static boolean enabled = false;
    static Instant time;
    static boolean initializedRpc = false;
    static boolean sent = true;
    static boolean triedReconnect = false;

    public static void initializeRpc() {
        if (!McciUtils.isOnMCCI() || !ModConfig.INSTANCE.enableDiscordStatus) {
            disconnect();
        } else if (!initializedRpc) {
            initializedRpc = true;
            CreateParams params = new CreateParams();
            params.setClientID(1012500697880731708L);
            params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);
            time = Instant.now();
            try {
                discordRPC = new Core(params);
                McciUtils.LOGGER.info("Discord RPC initialized");
                enabled = true;
                Thread callbacks = new Thread(() -> {
                    while (enabled) {
                        discordRPC.runCallbacks();
                        try {
                            Thread.sleep(16);//run callbacks at 60fps
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Thread.currentThread().interrupt();
                });
                callbacks.start();
            } catch (Exception e) {
                McciUtils.LOGGER.info("[WARN] An error occurred while trying to start the core, is Discord running?");
                enabled = false;
                sent = false;
            }
        }
    }

    public static void updateRPC(String location) {
        if (!ModConfig.INSTANCE.enableDiscordStatus) return;
        if (!initializedRpc) {
            initializeRpc();
        }
        try (Activity activity = new Activity()) {
            activity.setDetails(Defaults.defaultDetails());
            activity.setState(location);
            activity.assets().setLargeImage("logo");
            activity.assets().setLargeText("MCCI Utils");
            activity.timestamps().setStart(Instant.ofEpochSecond(time.toEpochMilli()));
            discordRPC.activityManager().updateActivity(activity);

            triedReconnect = false;
        } catch (Exception e) {
            e.printStackTrace();
            if (!triedReconnect)
                reconnect();
            else
                try {
                    enabled = false;
                    discordRPC.close();
                } catch (Throwable ignored) {
                }
        }
    }

    public static void reconnect() {
        triedReconnect = true;
        McciUtils.LOGGER.info("Trying to reconnect to Discord RPC");
        try {
            enabled = false;
            discordRPC.close();
        } catch (Throwable ignored) {
        }
        initializeRpc();
    }

    public static void disconnect() {
        initializedRpc = false;
        if (enabled) {
            try {
                McciUtils.LOGGER.info("Disconnecting from Discord RPC");
                discordRPC.close();
            } catch (GameSDKException e) {
                e.printStackTrace();
            }
            enabled = false;
        }
    }

    public static class Defaults {
        //This nested class stores all the default items for Rich Presence

        public static String defaultDetails() {
            return switch (ModConfig.INSTANCE.customDetails) {
                case IP -> "IP: play.mccisland.net";
                case USERNAME -> "Playing as " + MinecraftClient.getInstance().getSession().getUsername();
                case MOD_VERSION -> "Using Version v" + McciUtils.modVersion;
            };
        }
    }
}
