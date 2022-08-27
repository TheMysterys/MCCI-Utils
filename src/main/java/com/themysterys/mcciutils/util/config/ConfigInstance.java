package com.themysterys.mcciutils.util.config;

public class ConfigInstance {

    public boolean enableDiscordStatus;
    public RPCustomDetails customDetails;
    public FriendNotificationOptions friendNotificationOptions;
    public boolean enableChatMentions;
    //public MentionColor chatMentionColor;

    public ConfigInstance() {
        // Status
        enableDiscordStatus = true;
        customDetails = RPCustomDetails.IP;
        // Friend Notification
        friendNotificationOptions = FriendNotificationOptions.BOTH;
        // Chat Mentions
        enableChatMentions = true;
        //chatMentionColor = MentionColor.YELLOW;

    }

    public enum RPCustomDetails {

        IP("ServerIP"),
        USERNAME("Username"),
        MOD_VERSION("Mod Version");

        RPCustomDetails(String option) {}
    }

    public enum FriendNotificationOptions {
        BOTH,
        POPUP,
        SOUND,
        OFF;
        FriendNotificationOptions() {}
    }

    public enum MentionColor {
        YELLOW("§e"),
        GOLD("§6"),
        GREEN("§a"),
        DARK_GREEN("§2"),
        RED("§c"),
        DARK_RED("§4"),
        BLUE("§9"),
        DARK_BLUE("§1"),
        AQUA("§b"),
        DARK_AQUA("§3"),
        PINK("§d"),
        PURPLE("§5"),
        NO_COLOR("");

        MentionColor(String color){}
    }


    // Config checks
    public boolean shouldShowFriendPopup() {
        return friendNotificationOptions == FriendNotificationOptions.BOTH || friendNotificationOptions == FriendNotificationOptions.POPUP;
    }

    public boolean shouldPlayFriendSound() {
        return friendNotificationOptions == FriendNotificationOptions.BOTH || friendNotificationOptions == FriendNotificationOptions.SOUND;
    }

}