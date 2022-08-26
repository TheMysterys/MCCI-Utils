package com.themysterys.mcciutils.Global;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.discord.DiscordRP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationListener {

    public static String worldName;
    public static String Lastlocation;

    /*
    * TODO: Look for a better way to optimize this. (Works well for now)
    *       Also need to look for a way to update settings in real time
    * */
    public static void listenWorld(){
        ClientWorld world = MinecraftClient.getInstance().world;
        if(McciUtils.isOnMCCI() && world != null) {
            ScoreboardObjective objective = world.getScoreboard().getObjectiveForSlot(1);
            if(objective != null) {
                if (!world.getRegistryKey().getValue().getPath().equals(worldName)) {
                    if (world.getRegistryKey().getValue().getPath().equals("limbo")) {
                        LocationID.updateLocationID("LIMBO");
                    } else {
                        Text rawLocation = objective.getDisplayName();
                        Pattern pattern = Pattern.compile("literal\\{([A-Z ]+)}", Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(rawLocation.toString());
                        while (matcher.find()) {
                            String location = matcher.group(1).strip();
                            if (location.length() > 0 && !location.equals(Lastlocation)) {
                                McciUtils.LOGGER.info("Location changed to " + location);
                                LocationID.updateLocationID(location);
                                worldName = world.getRegistryKey().getValue().getPath();
                                McciUtils.LOGGER.info("World changed to " + worldName);
                            }
                        }
                    }
                    worldCheck();
                }
            }
        } else if (!McciUtils.isOnMCCI()) {
            DiscordRP.closeRP();
            LocationID.updateLocationID("");
        }
    }

    private static void worldCheck(){
        LocationID.sendPresence();
    }
}
