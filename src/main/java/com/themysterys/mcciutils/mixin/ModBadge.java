package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.websockets.ModUsers;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(PlayerListHud.class)
public class ModBadge {

    @Inject(at = @At("RETURN"), method = "getPlayerName", cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<Text> cir) {
        Text name = cir.getReturnValue();
        if (!McciUtils.isOnMCCI()) {
            if (name.getString().contains("\uE07D")) {
                cir.setReturnValue(Text.of(name.getString().replace("\uE07D", "")));
            }
        } else {
            Pattern pattern = Pattern.compile(".. ?([a-zA-Z_\\d]+)");
            Matcher matcher = pattern.matcher(name.getString());
            // set username to result of group 1
            String username = matcher.find() ? matcher.group(1) : null;
            // Return original name if username is null
            if (username == null || username.length() == 0) {
                return;
            }
            if (ModUsers.containsUser(username)) {
                MutableText newName = MutableText.of(TextContent.EMPTY);
                for (Text value : name.getSiblings()) {
                    if (value.getString().contains(username)) {
                        newName.append(value).append(Text.literal("\uEFEF").setStyle(Style.EMPTY.withFont(new Identifier("mcciutils:icon"))).formatted(ModUsers.getRankColor(username)));
                    } else if (!value.getString().contains("....")){
                        newName.append(value);
                    }
                }
                cir.setReturnValue(newName);
            }
        }
    }
}