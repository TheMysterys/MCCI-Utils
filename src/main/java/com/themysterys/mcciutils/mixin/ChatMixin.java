package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.McciUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Pattern;

@Mixin(ChatHud.class)
public class ChatMixin {

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void addMessage(Text message, CallbackInfo ci) {
        McciUtils.LOGGER.info("Message: " + message.getString());
        if (message.getString().contains("has come online!")) {
            Pattern pattern = Pattern.compile("([a-zA-Z_]{3,16}) has come online!");
            String name = pattern.matcher(message.getString()).replaceFirst("$1");
            if (name != null) {
                ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
                McciUtils.LOGGER.info("Adding toast for " + name);
                SystemToast.add(toastManager, SystemToast.Type.PERIODIC_NOTIFICATION, Text.of("Friend Joined"), Text.of(name));
            }
        } else if (message.getString().contains("has gone offline.")) {
            Pattern pattern = Pattern.compile("([a-zA-Z_]{3,16}) has gone offline.");
            String name = pattern.matcher(message.getString()).replaceFirst("$1");
            if (name != null) {
                ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
                McciUtils.LOGGER.info("Adding toast for " + name);
                SystemToast.add(toastManager, SystemToast.Type.PERIODIC_NOTIFICATION, Text.of("Friend Left"), Text.of(name));
            }
        }
    }
}
