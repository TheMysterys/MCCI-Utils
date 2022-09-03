package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.McciToast;
import com.themysterys.mcciutils.util.config.ConfigInstance;
import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.themysterys.mcciutils.util.sounds.Sounds.*;

@Mixin(ChatHud.class)
public class ChatMixin {

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void addMessage(Text message, CallbackInfo ci) {
        if (!McciUtils.isOnMCCI()) {
            return;
        }

        // Friend notifications
        if (ModConfig.INSTANCE.friendNotifications != ConfigInstance.POPUP_NOTIFICATION_OPTIONS.OFF) {
            if (Objects.equals(message.getStyle().getColor(), TextColor.fromRgb(0xFFFF00))) {
                if (message.getString().matches("^(\\w{1,16}) has come online!")) {
                    Pattern pattern = Pattern.compile("^(\\w{1,16}) has come online!");
                    String name = pattern.matcher(message.getString()).replaceFirst("$1");
                    if (name != null) {
                        if (ModConfig.INSTANCE.shouldShowFriendPopup()) {
                            ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
                            McciToast.add(toastManager, McciToast.Type.FRIEND_JOIN, Text.of(name));
                        }
                        if (ModConfig.INSTANCE.shouldPlayFriendSound() && MinecraftClient.getInstance().player != null) {
                            MinecraftClient.getInstance().player.playSound(FRIEND_SOUND, SoundCategory.MASTER, 1F, 1F);
                        }
                    }
                } else if (message.getString().matches("^(\\w{1,16}) has gone offline.")) {
                    Pattern pattern = Pattern.compile("^(\\w{1,16}) has gone offline.");
                    String name = pattern.matcher(message.getString()).replaceFirst("$1");
                    if (name != null) {
                        if (ModConfig.INSTANCE.shouldShowFriendPopup()) {
                            ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
                            McciToast.add(toastManager, McciToast.Type.FRIEND_LEAVE, Text.of(name));
                        }
                        if (ModConfig.INSTANCE.shouldPlayFriendSound() && MinecraftClient.getInstance().player != null) {
                            MinecraftClient.getInstance().player.playSound(FRIEND_SOUND, SoundCategory.MASTER, 1F, 1F);
                        }
                    }
                }
           }
        }

        // Chat mentions
        if (ModConfig.INSTANCE.shouldPlayMentionSound()) {
            if (MinecraftClient.getInstance().player != null) {
                String username = MinecraftClient.getInstance().getSession().getUsername();
                if (message.getString().matches("^.. \\w{1,16}: ?.*" + username + ".*")) {
                    MinecraftClient.getInstance().player.playSound(MENTION_SOUND, SoundCategory.MASTER, 1F,1F);
                }
            }
        }

        // Quest Completion
        if (ModConfig.INSTANCE.questCompleteOptions != ConfigInstance.POPUP_NOTIFICATION_OPTIONS.OFF) {
            if (message.getString().matches("^\\(.\\) Quest complete: (.*)")){
                Pattern pattern = Pattern.compile("^\\(.\\) Quest complete: (.*)");
                String questName = pattern.matcher(message.getString()).replaceFirst("$1");
                if (questName != null) {
                    if (ModConfig.INSTANCE.shouldShowQuestPopup()) {
                        ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
                        McciToast.add(toastManager, McciToast.Type.QUEST_COMPLETE, Text.of(questName));
                    }
                    if (ModConfig.INSTANCE.shouldPlayQuestSound() && MinecraftClient.getInstance().player != null) {
                        MinecraftClient.getInstance().player.playSound(QUEST_COMPLETE_SOUND, SoundCategory.MASTER, 1F, 1F);
                    }
                }
            }

        }

        // Other notifiations
    }

    // Use for mentions (Maybe will add more in the future, unsure)
    @ModifyVariable(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;)V", argsOnly = true)
    private Text modifyMessage(Text value) {
        if (!McciUtils.isOnMCCI() || !ModConfig.INSTANCE.shouldColorMention()) {
            return value;
        }

        String username = MinecraftClient.getInstance().getSession().getUsername();
        Pattern pattern = Pattern.compile("^.. \\w{1,16}: ?.*" + username + ".*");
        if (pattern.matcher(value.getString()).find()) {
            List<Text> fullMessage = value.getSiblings();

            MutableText newMessage = MutableText.of(TextContent.EMPTY);
            if (!fullMessage.get(fullMessage.size() - 1).toString().contains(username)) return value;

            for (int i = 0; i < fullMessage.size(); i++) {
                if (i == fullMessage.size() - 1) {
                    String[] split = fullMessage.get(i).getString().split(username);
                    newMessage.append(switch (split.length) {
                        case 0 -> Text.literal(username).formatted(ModConfig.INSTANCE.chatMentionColor.getColor());
                        case 1 -> Text.literal(split[0]).append(Text.literal(username).formatted(ModConfig.INSTANCE.chatMentionColor.getColor()));
                        default -> Text.literal(split[0]).append(Text.literal(username).formatted(ModConfig.INSTANCE.chatMentionColor.getColor())).append(Text.literal(split[1]));
                    });
                } else {
                    newMessage.append(fullMessage.get(i));
                }
            }

            return newMessage;
        } else {
            return value;
        }
    }
}
