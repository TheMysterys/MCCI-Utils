package com.themysterys.mcciutils.util.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.themysterys.mcciutils.util.config.ConfigInstance.RPCustomDetails;
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
                        Text.translatable("mcciutils.config.enableDiscordStatus.line1"),
                        Text.translatable("mcciutils.config.enableDiscordStatus.line2")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.enableDiscordStatus = val)
                .build()
        );

        discordCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.customDetails"), RPCustomDetails.class, ModConfig.INSTANCE.customDetails)
                .setDefaultValue(RPCustomDetails.IP)
                .setTooltip(
                        Text.translatable("mcciutils.config.customDetails.line1"),
                        Text.translatable("mcciutils.config.customDetails.line2"),
                        Text.translatable("mcciutils.config.customDetails.line3"),
                        Text.translatable("mcciutils.config.customDetails.line4")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.customDetails = val)
                .build()
        );

        ConfigCategory friendNotificationCategory = configBuilder.getOrCreateCategory(Text.translatable("mcciutils.category.friendNotifications"));

        friendNotificationCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.friendNotificationOptions"), ConfigInstance.FriendNotificationOptions.class, ModConfig.INSTANCE.friendNotificationOptions)
                .setDefaultValue(ConfigInstance.FriendNotificationOptions.BOTH)
                .setTooltip(
                        Text.translatable("mcciutils.config.friendNotificationOptions.line1"),
                        Text.translatable("mcciutils.config.friendNotificationOptions.line2"),
                        Text.translatable("mcciutils.config.friendNotificationOptions.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.friendNotificationOptions = val)
                .build()
        );

        ConfigCategory chatMentionCategory = configBuilder.getOrCreateCategory(Text.translatable("mcciutils.category.chatMention"));

        chatMentionCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.enableChatMentions"), ConfigInstance.MentionOptions.class, ModConfig.INSTANCE.chatMentionsOptions)
                .setDefaultValue(ConfigInstance.MentionOptions.BOTH)
                .setTooltip(
                        Text.translatable("mcciutils.config.enableChatMentions.line1"),
                        Text.translatable("mcciutils.config.enableChatMentions.line2"),
                        Text.translatable("mcciutils.config.enableChatMentions.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.chatMentionsOptions = val)
                .build()
        );

        chatMentionCategory.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.chatMentionColor"), ConfigInstance.MentionColor.class, ModConfig.INSTANCE.chatMentionColor)
                .setDefaultValue(ConfigInstance.MentionColor.YELLOW)
                .setTooltip(
                        Text.translatable("mcciutils.config.chatMentionColor.line1")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.chatMentionColor = val)
                .build()
        );

        return configBuilder;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> builder().setParentScreen(parent).build();
    }
}