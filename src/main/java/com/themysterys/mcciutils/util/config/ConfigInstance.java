package com.themysterys.mcciutils.util.config;

import net.minecraft.util.Formatting;

public class ConfigInstance {

    // Discord
    public boolean enableDiscordStatus;
    public RPCustomDetails customDetails;

    // Notifications
    public POPUP_NOTIFICATION_OPTIONS friendNotifications;
    public POPUP_NOTIFICATION_OPTIONS questCompleteOptions;
    public POPUP_NOTIFICATION_OPTIONS badgeAchievedOptions;

    // Chat
    public CHAT_NOTIFICATION_OPTIONS chatMentions;
    public COLOR_OPTIONS chatMentionColor;
    public SPAM_OPTIONS stackSpam;
    public boolean autoGG;

    // Misc
    public boolean hideSlotHighlight;
    public boolean hidePanorama;

    // Performance
    public boolean disableGlowing;
    public boolean disableBossbar;
    public boolean disableScoreboard;

    public ConfigInstance() {
        // Discord
        enableDiscordStatus = true;
        customDetails = RPCustomDetails.IP;
        // Notifications
        friendNotifications = POPUP_NOTIFICATION_OPTIONS.BOTH;
        questCompleteOptions = POPUP_NOTIFICATION_OPTIONS.BOTH;
        badgeAchievedOptions = POPUP_NOTIFICATION_OPTIONS.BOTH;

        // Chat
        chatMentions = CHAT_NOTIFICATION_OPTIONS.BOTH;
        chatMentionColor = COLOR_OPTIONS.YELLOW;
        stackSpam = SPAM_OPTIONS.CONSECUTIVE;
        autoGG = true;

        // Misc
        hideSlotHighlight = true;
        hidePanorama = false;

        // Performance
        disableGlowing = false;
        disableBossbar = false;
        disableScoreboard = false;

    }

    public enum RPCustomDetails {

        IP,
        USERNAME,
        MOD_VERSION;

        RPCustomDetails() {}
    }

    public enum POPUP_NOTIFICATION_OPTIONS {
        BOTH,
        POPUP,
        SOUND,
        OFF;
        POPUP_NOTIFICATION_OPTIONS() {}
    }

    public enum CHAT_NOTIFICATION_OPTIONS {
        BOTH,
        COLOR,
        SOUND,
        OFF;

        CHAT_NOTIFICATION_OPTIONS() {}
    }

    public enum COLOR_OPTIONS {
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
        COLOR_OPTIONS(Formatting color){
            this.formatting = color;
        }

        public Formatting getColor() {
            return formatting;
        }
    }

    public enum SPAM_OPTIONS {
        CONSECUTIVE,
        ALL,
        OFF;
        SPAM_OPTIONS() {}
    }

    // Config checks
    public boolean shouldShowFriendPopup() {
        return friendNotifications == POPUP_NOTIFICATION_OPTIONS.BOTH || friendNotifications == POPUP_NOTIFICATION_OPTIONS.POPUP;
    }

    public boolean shouldPlayFriendSound() {
        return friendNotifications == POPUP_NOTIFICATION_OPTIONS.BOTH || friendNotifications == POPUP_NOTIFICATION_OPTIONS.SOUND;
    }

    public boolean shouldColorMention() {
        return chatMentions == CHAT_NOTIFICATION_OPTIONS.BOTH || chatMentions == CHAT_NOTIFICATION_OPTIONS.COLOR;
    }

    public boolean shouldPlayMentionSound() {
        return chatMentions == CHAT_NOTIFICATION_OPTIONS.BOTH || chatMentions == CHAT_NOTIFICATION_OPTIONS.SOUND;
    }

    public boolean shouldShowQuestPopup() {
        return questCompleteOptions == POPUP_NOTIFICATION_OPTIONS.BOTH || questCompleteOptions == POPUP_NOTIFICATION_OPTIONS.POPUP;
    }

    public boolean shouldPlayQuestSound() {
        return questCompleteOptions == POPUP_NOTIFICATION_OPTIONS.BOTH || questCompleteOptions == POPUP_NOTIFICATION_OPTIONS.SOUND;
    }

}