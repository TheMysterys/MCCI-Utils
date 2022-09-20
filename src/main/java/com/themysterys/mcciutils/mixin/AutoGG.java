package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ChatHud.class)
public class AutoGG {

    @Inject(at = @At("TAIL"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void onChatMessage(Text message, CallbackInfo ci) {
        if (!McciUtils.isOnMCCI() || !ModConfig.INSTANCE.autoGG) return;
        if (Objects.equals(message.getStyle().getColor(), TextColor.fromRgb(0x65FFFF))) {
            if(message.getString().matches("\\[.] Game over!")) {
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendCommand("l gg");
                }
            }
        }
    }
}
