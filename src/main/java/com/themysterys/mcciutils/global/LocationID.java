package com.themysterys.mcciutils.global;

import com.themysterys.mcciutils.util.discord.DiscordRP;

public class LocationID {

    public static String locationID = "";

    public static void updateLocationID(String location){
        locationID = location;
    }

    public static void sendPresence(){
        String locationString = generateStateString();
        if(!locationString.equals("")){
            DiscordRP.updateRPC(locationString);
        }
    }

    public static String generateStateString() {
        if (locationID == null) {
            return "Unknown";
        }
        switch (locationID) {
            // Lobbies
            case "MAIN ISLAND" -> {
                return "On the Main Island";
            }
            case "SLIME FACTORY" -> {
                return "In HITW lobby";
            }
            case "BACK ALLEY" -> {
                return "In Battle Box lobby";
            }
            // Mixed
            case "TGTTOS" -> {
                return "In TGTTOS game/lobby";
            }
            case "SKY BATTLE" -> {
                return "In Sky Battle game/lobby";
            }
            // Gamemodes
            case "HOLE IN THE WALL" -> {
                return "Playing HITW";
            }
            case "BATTLE BOX" -> {
                return "Playing Battle Box";
            }
            // Limbo
            case "LIMBO" -> {
                return "In Limbo";
            }

            default -> {
                return "Unknown";
            }
        }
    }
}
