package com.themysterys.mcciutils.mixin.menu;

import com.themysterys.mcciutils.McciUtils;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MCCDay {

    @Shadow @Nullable private String splashText;

    @Inject(at = @At("HEAD"), method = "init()V")
    public void isMCCDay(CallbackInfo ci) {
        if (McciUtils.mccAPI.isMCCDay()) {
            splashText = "MCC Day!";
        }
    }
}
