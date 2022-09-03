package com.themysterys.mcciutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Yellow {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("yellow").executes(ctx -> {
            ctx.getSource().getPlayer().playSound(SoundEvents.GOAT_HORN_SOUNDS.get(0), SoundCategory.MASTER, 1F, 1F);
            ctx.getSource().sendFeedback(Text.literal("The Yellow Army calls!").formatted(Formatting.YELLOW));
            return 1;
        }));
    }
}
