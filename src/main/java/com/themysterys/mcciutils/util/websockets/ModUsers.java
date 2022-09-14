package com.themysterys.mcciutils.util.websockets;

import net.minecraft.util.Formatting;

import java.util.HashMap;

public class ModUsers {

    public enum rankColors {
        player(Formatting.WHITE),
        friend(Formatting.DARK_AQUA),
        developer(Formatting.LIGHT_PURPLE),
        mystery(Formatting.BLUE);

        public final Formatting color;
        rankColors(Formatting color) {
            this.color = color;
        }


    }

    private static final HashMap<String, String> users = new HashMap<>();

    public static void addUser(String username, String rank) {
        users.put(username, rank);
    }

    public static void clear() {
        users.clear();
    }

    public static boolean containsUser(String username) {
        return users.containsKey(username);
    }

    public static Formatting getRankColor(String username) {
        return rankColors.valueOf(users.get(username)).color;
    }


}
