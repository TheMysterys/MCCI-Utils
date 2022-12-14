package com.themysterys.mcciutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.themysterys.mcciutils.McciUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Goat {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("goat").executes(ctx -> {
            if (!McciUtils.isOnMCCI()) {
                ctx.getSource().sendError(Text.of("You are not on MCC Island!"));
                return 1;
            }
            ctx.getSource().getPlayer().playSound(SoundEvents.ENTITY_GOAT_SCREAMING_AMBIENT, SoundCategory.MASTER, 1F, 1F);
            ctx.getSource().sendFeedback(Text.literal("Gray Goats!").formatted(Formatting.GRAY));
            return 1;
        }));
    }
}
