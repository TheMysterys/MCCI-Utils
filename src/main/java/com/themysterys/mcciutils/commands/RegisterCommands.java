package com.themysterys.mcciutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class RegisterCommands {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        Goat.register(dispatcher);
        Commands.register(dispatcher);
        Yellow.register(dispatcher);
        Discord.register(dispatcher);
    }
}
