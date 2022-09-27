package com.themysterys.mcciutils.mixin.menu;

import com.themysterys.mcciutils.util.api.DateAPI;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class SplashSelector {

    @Shadow @Nullable private String splashText;

    @Inject(at = @At("TAIL"), method = "init()V")
    public void isMCCDay(CallbackInfo ci) {
        if (DateAPI.isMCCIBirthday()) {
            String age = DateAPI.getMCCIAge();
            this.splashText = "Happy %s Birthday, MCC Island!".formatted(age);
        } else if (DateAPI.isMCCDay()) {
            splashText = "MCC Day!";
        }
    }
}
