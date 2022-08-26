package com.themysterys.mcciutils.Global;

import com.themysterys.mcciutils.util.discord.DiscordRP;

public class LocationID {

    public static String locationID = "";

    public static void updateLocationID(String location){
        locationID = location;
    }

    public static void sendPresence(){
        String state = generateStateString();
        if(!state.equals("")){
            DiscordRP.updateStatus(state, DiscordRP.Defaults.defaultDetails());
        }
    }

    public static String generateStateString() {
        switch (locationID) {
            // Lobbies
            case "MAIN ISLAND" -> {
                return "In main lobby";
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
                return "Player Battle Box";
            }
            // Limbo
            case "LIMBO" -> {
                return "In limbo";
            }

            default -> {
                return "Unknown";
            }
        }
    }
}
