package com.themysterys.mcciutils.util.config;

import net.minecraft.util.Formatting;

public class ConfigInstance {

    public boolean enableDiscordStatus;
    public RPCustomDetails customDetails;
    public FriendNotificationOptions friendNotificationOptions;
    public MentionOptions chatMentionsOptions;
    public MentionColor chatMentionColor;

    public ConfigInstance() {
        // Status
        enableDiscordStatus = true;
        customDetails = RPCustomDetails.IP;
        // Friend Notification
        friendNotificationOptions = FriendNotificationOptions.BOTH;
        // Chat Mentions
        chatMentionsOptions = MentionOptions.BOTH;
        chatMentionColor = MentionColor.YELLOW;

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

    public enum MentionOptions {
        BOTH,
        COLOR,
        SOUND,
        OFF;

        MentionOptions() {}
    }

    public enum MentionColor {
        YELLOW(Formatting.YELLOW),
        GOLD(Formatting.GOLD),
        GREEN(Formatting.GREEN),
        DARK_GREEN(Formatting.DARK_GREEN),
        RED(Formatting.RED),
        DARK_RED(Formatting.DARK_RED),
        BLUE(Formatting.BLUE),
        DARK_BLUE(Formatting.DARK_BLUE),
        AQUA(Formatting.AQUA),
        DARK_AQUA(Formatting.DARK_AQUA),
        PINK(Formatting.LIGHT_PURPLE),
        PURPLE(Formatting.DARK_PURPLE);
        private final Formatting formatting;
        MentionColor(Formatting color){
            this.formatting = color;
        }

        public Formatting getColor() {
            return formatting;
        }
    }


    // Config checks
    public boolean shouldShowFriendPopup() {
        return friendNotificationOptions == FriendNotificationOptions.BOTH || friendNotificationOptions == FriendNotificationOptions.POPUP;
    }

    public boolean shouldPlayFriendSound() {
        return friendNotificationOptions == FriendNotificationOptions.BOTH || friendNotificationOptions == FriendNotificationOptions.SOUND;
    }

    public boolean shouldColorMention() {
        return chatMentionsOptions == MentionOptions.BOTH || chatMentionsOptions == MentionOptions.COLOR;
    }

    public boolean shouldPlayMentionSound() {
        return chatMentionsOptions == MentionOptions.BOTH || chatMentionsOptions == MentionOptions.SOUND;
    }

}