package com.themysterys.mcciutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.themysterys.mcciutils.McciUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Discord {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("discord").executes(ctx -> {
            ctx.getSource().sendFeedback(Text.literal("Join MCC's Discord here: ").append(Text.literal("https://discord.gg/mcc").styled(style -> style.withColor(Formatting.BLUE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/mcc")))));
            return 1;
        }));
    }
}
