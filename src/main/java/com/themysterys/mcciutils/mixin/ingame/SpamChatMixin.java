package com.themysterys.mcciutils.mixin.ingame;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.chat.StackedMessage;
import com.themysterys.mcciutils.util.chat.BetterOrderedText;
import com.themysterys.mcciutils.util.config.ConfigInstance;
import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChatHud.class)
public abstract class SpamChatMixin {

    @Shadow
    @Final
    private List<ChatHudLine> messages;
    @Shadow
    @Final
    private List<ChatHudLine.Visible> visibleMessages;

    @Shadow
    public abstract void addMessage(Text message);

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;)V", cancellable = true)
    private void modifyMessage(Text message, CallbackInfo ci) {
        if (ModConfig.INSTANCE.stackSpam == ConfigInstance.SPAM_OPTIONS.OFF) return;
        var messageString = message.getString();
        var trimmedMessage = messageString.trim();
        if (trimmedMessage.isEmpty() || trimmedMessage.isBlank()) return;

        var optionalCompactedMessage = McciUtils.COMPACTED_MESSAGES
                .stream()
                .filter(it -> it.equals(message))
                .findFirst();


        if (optionalCompactedMessage.isEmpty()) {
            McciUtils.COMPACTED_MESSAGES.add(new StackedMessage(message));
            return;
        }

        var compactedMessage = optionalCompactedMessage.get();


        if (ModConfig.INSTANCE.stackSpam == ConfigInstance.SPAM_OPTIONS.CONSECUTIVE) {
            if (messages.isEmpty()) return;

            var previousMessage = messages.get(0).content();
            if (previousMessage.getString().equals(messageString)) {
                messages.remove(0);
                visibleMessages.remove(0);
                compactedMessage.incrementOccurrences();
                addMessage(compactedMessage.getModifiedMessage());
            } else if (compactedMessage.getModifiedMessage().equals(previousMessage)) {
                messages.remove(0);
                visibleMessages.remove(0);
                compactedMessage.incrementOccurrences();
                addMessage(compactedMessage.getModifiedMessage());
            } else {
                compactedMessage.resetOccurrences();
                return;
            }
        } else {
            // Remove existing message(s) from chat
            for (ChatHudLine chatHudLine : new ArrayList<>(messages)) {
                var text = chatHudLine.content();
                if (text.getString().equals(messageString)) {
                    McciUtils$removeMessageByText(text);
                } else if (compactedMessage.getModifiedMessage().equals(text)) {
                    McciUtils$removeMessageByText(text);
                }
            }
            compactedMessage.incrementOccurrences();
            addMessage(compactedMessage.getModifiedMessage());
        }

        ci.cancel();
    }

    @Unique
    private void McciUtils$removeMessageByText(Text text) {
        visibleMessages.removeIf((message) -> BetterOrderedText.getString(message.content()).equals(text.getString()));
        messages.removeIf((message) -> message.content().getString().equals(text.getString()));
    }
}
