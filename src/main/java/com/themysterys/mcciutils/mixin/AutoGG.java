package com.themysterys.mcciutils.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class AutoGG {

    @Inject(at = @At("TAIL"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void onChatMessage(Text message, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        MinecraftClient.getInstance().player.sendChatMessage("gg", Text.of("gg"));
    }
}
