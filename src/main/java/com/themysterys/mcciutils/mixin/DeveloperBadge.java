package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.McciUtils;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class DeveloperBadge {


    @Inject(at = @At("RETURN"), method = "getPlayerName", cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<Text> cir) {
        Text name = cir.getReturnValue();
        if (!McciUtils.isOnMCCI()) {
            if (name.getString().contains("\uE07D")) {
                cir.setReturnValue(Text.of(name.getString().replace("\uE07D", "")));
            }
        } else {
            if (name.getString().contains("TheMysterys")) {
                MutableText mutableText = name.copy();
                mutableText.append(Text.literal("\uE07D").setStyle(Style.EMPTY.withFont(new Identifier("mcc:icon"))).formatted(Formatting.WHITE));
                cir.setReturnValue(mutableText);
            }
        }
    }
}