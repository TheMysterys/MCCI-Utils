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

        ConfigCategory general = configBuilder.getOrCreateCategory(Text.translatable("mcciutils.config.general"));

        general.addEntry(configBuilder.entryBuilder()
                .startBooleanToggle(Text.translatable("mcciutils.config.enableDiscordStatus"), ModConfig.INSTANCE.enableDiscordStatus)
                .setDefaultValue(true)
                .setTooltip(
                        Text.translatable("mcciutils.config.enableDiscordStatus.line1"),
                        Text.translatable("mcciutils.config.enableDiscordStatus.line2")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.enableDiscordStatus = val)
                .build()
        );

        general.addEntry(configBuilder.entryBuilder()
                .startEnumSelector(Text.translatable("mcciutils.config.customDetails"), RPCustomDetails.class, ModConfig.INSTANCE.customDetails)
                .setDefaultValue(RPCustomDetails.IP)
                .setTooltip(
                        Text.translatable("mcciutils.config.customDetails.line1"),
                        Text.translatable("mcciutils.config.customDetails.line2"),
                        Text.translatable("mcciutils.config.customDetails.line3")
                )
                .setSaveConsumer(val -> ModConfig.INSTANCE.customDetails = val)
                .build()
        );

        return configBuilder;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> builder().setParentScreen(parent).build();
    }
}