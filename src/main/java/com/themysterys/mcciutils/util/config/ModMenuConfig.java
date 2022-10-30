package com.themysterys.mcciutils.util.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.themysterys.mcciutils.global.LocationID;
import com.themysterys.mcciutils.util.config.ConfigInstance.RPCustomDetails;
import com.themysterys.mcciutils.util.discord.DiscordRP;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.text.Text;


public class ModMenuConfig implements ModMenuApi {

    public static ConfigBuilder builder() {
        ConfigBuilder configBuilder = ConfigBuilder.create()
                .setTitle(Text.translatable("key.category.mcciutils"))
                .setEditable(true)
                .setTransparentBackground(true)
                .setSavingRunnable(ModConfig::writeJson);


        ConfigCategory discordCategory = configBuilder.getOrCreateCategory(Text.translatable("mcciutils.category.discord"));

        discordCategory.addEntry(configBuilder.entryBuilder()
                .startBooleanToggle(Text.translatable("mcciutils.config.enableDiscordStatus"), ModConfig.INSTANCE.enableDiscordStatus)
                .setDefaultValue(true)
                .setTooltip(
                        Text.translatable("mcciutils.config.enableDiscordStatus.line1")
                )
                .setSaveConsumer(val -> {
                    ModConfig.INSTANCE.enableDiscordStatus = val;
                    if (!val) {
                        DiscordRP.disconnect();
                    } else {
                        DiscordRP.initializeRpc();
                    }
                })
                .build()
        );

        discordCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.customDetails"), RPCustomDetails.class, ModConfig.INSTANCE.customDetails)
                .setDefaultValue(RPCustomDetails.IP)
                .setTooltip(
                        Text.translatable("mcciutils.config.customDetails.line1"),
                        Text.translatable("mcciutils.config.customDetails.line2"),
                        Text.translatable("mcciutils.config.customDetails.line3")
                )
                .setSaveConsumer(val -> {
                    ModConfig.INSTANCE.customDetails = val;
                    LocationID.sendPresence();
                })
                .build()
        );


        ConfigCategory notificationCategory = configBuilder.getOrCreateCategory(Text.translatable("mcciutils.category.notifications"));

        notificationCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.friendNotificationOptions"), ConfigInstance.POPUP_NOTIFICATION_OPTIONS.class, ModConfig.INSTANCE.friendNotifications)
                .setDefaultValue(ConfigInstance.POPUP_NOTIFICATION_OPTIONS.BOTH)
                .setTooltip(
                        Text.translatable("mcciutils.config.friendNotificationOptions.line1"),
                        Text.translatable("mcciutils.config.friendNotificationOptions.line2"),
                        Text.translatable("mcciutils.config.friendNotificationOptions.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.friendNotifications = val)
                .build()
        );

        notificationCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.questNotificationOptions"), ConfigInstance.POPUP_NOTIFICATION_OPTIONS.class, ModConfig.INSTANCE.questCompleteOptions)
                .setDefaultValue(ConfigInstance.POPUP_NOTIFICATION_OPTIONS.BOTH)
                .setTooltip(
                        Text.translatable("mcciutils.config.questNotificationOptions.line1"),
                        Text.translatable("mcciutils.config.questNotificationOptions.line2"),
                        Text.translatable("mcciutils.config.questNotificationOptions.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.questCompleteOptions = val)
                .build()
        );

        notificationCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.achievementNotificationOptions"), ConfigInstance.POPUP_NOTIFICATION_OPTIONS.class, ModConfig.INSTANCE.achievementUnlockOptions)
                .setDefaultValue(ConfigInstance.POPUP_NOTIFICATION_OPTIONS.BOTH)
                .setTooltip(
                        Text.translatable("mcciutils.config.achievementNotificationOptions.line1"),
                        Text.translatable("mcciutils.config.achievementNotificationOptions.line2"),
                        Text.translatable("mcciutils.config.achievementNotificationOptions.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.achievementUnlockOptions = val)
                .build()
        );


        ConfigCategory chatCategory = configBuilder.getOrCreateCategory(Text.translatable("mcciutils.category.chat"));

        chatCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.enableChatMentions"), ConfigInstance.CHAT_NOTIFICATION_OPTIONS.class, ModConfig.INSTANCE.chatMentions)
                .setDefaultValue(ConfigInstance.CHAT_NOTIFICATION_OPTIONS.BOTH)
                .setTooltip(
                        Text.translatable("mcciutils.config.enableChatMentions.line1"),
                        Text.translatable("mcciutils.config.enableChatMentions.line2"),
                        Text.translatable("mcciutils.config.enableChatMentions.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.chatMentions = val)
                .build()
        );

        chatCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.chatMentionColor"), ConfigInstance.COLOR_OPTIONS.class, ModConfig.INSTANCE.chatMentionColor)
                .setDefaultValue(ConfigInstance.COLOR_OPTIONS.YELLOW)
                .setTooltip(
                        Text.translatable("mcciutils.config.chatMentionColor.line1")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.chatMentionColor = val)
                .build()
        );

        chatCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.stackSpam"), ConfigInstance.SPAM_OPTIONS.class, ModConfig.INSTANCE.stackSpam)
                .setDefaultValue(ConfigInstance.SPAM_OPTIONS.CONSECUTIVE)
                .setTooltip(
                        Text.translatable("mcciutils.config.stackSpam.line1"),
                        Text.translatable("mcciutils.config.stackSpam.line2"),
                        Text.translatable("mcciutils.config.stackSpam.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.stackSpam = val)
                .build()
        );

        chatCategory.addEntry(configBuilder.entryBuilder()
                .startBooleanToggle(Text.translatable("mcciutils.config.autoGG"), ModConfig.INSTANCE.autoGG)
                .setDefaultValue(true)
                .setTooltip(
                        Text.translatable("mcciutils.config.autoGG.line1")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.autoGG = val)
                .build()
        );


        ConfigCategory miscCategory = configBuilder.getOrCreateCategory(Text.translatable("mcciutils.category.misc"));

        miscCategory.addEntry(configBuilder.entryBuilder()
                .startBooleanToggle(Text.translatable("mcciutils.config.hideSlotHighlight"), ModConfig.INSTANCE.hideSlotHighlight)
                .setDefaultValue(true)
                .setTooltip(
                        Text.translatable("mcciutils.config.hideSlotHighlight.line1")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.hideSlotHighlight = val)
                .build()
        );

        miscCategory.addEntry(configBuilder.entryBuilder()
                .startBooleanToggle(Text.translatable("mcciutils.config.hidePanorama"), ModConfig.INSTANCE.hidePanorama)
                .setDefaultValue(false)
                .setTooltip(
                        Text.translatable("mcciutils.config.hidePanorama.line1")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.hidePanorama = val)
                .build()
        );

        return configBuilder;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> builder().setParentScreen(parent).build();
    }
}