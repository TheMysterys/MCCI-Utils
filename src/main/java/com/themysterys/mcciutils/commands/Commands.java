package com.themysterys.mcciutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Commands {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("commands").executes(ctx -> {
            AdvancementsScreen advancementsScreen = new AdvancementsScreen(ctx.getSource().getPlayer().networkHandler.getAdvancementHandler());
            ctx.getSource().getClient().send(() -> ctx.getSource().getClient().setScreen(advancementsScreen));

            return 1;
        }));
    }
}
