package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.util.discord.DiscordRP;
import net.minecraft.network.ClientConnection;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class DisconnectMixin {

    @Inject(at = @At("HEAD"), method = "disconnect")
    private void disconnect(Text disconnectReason, CallbackInfo ci) {
        if (disconnectReason.getString().equals("Quitting")) {
            DiscordRP.closeRP();
        }
    }
}
