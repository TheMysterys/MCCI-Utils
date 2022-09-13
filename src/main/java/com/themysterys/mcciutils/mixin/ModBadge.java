package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.McciUtils;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
            if (name.getString().contains("TheMysterys")) {
                MutableText newName = MutableText.of(TextContent.EMPTY);
                for (Text value : name.getSiblings()) {
                    if (value.getString().contains("TheMysterys")) {
                        newName.append(value).append(Text.literal("\uE07D").setStyle(Style.EMPTY.withFont(new Identifier("mcc:icon"))).formatted(Formatting.WHITE));
                    } else if (!value.getString().contains("....")){
                        newName.append(value);
                    }
                }
                cir.setReturnValue(newName);
            }
        }
    }
}