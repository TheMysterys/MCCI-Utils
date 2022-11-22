package com.themysterys.mcciutils.mixin.ingame;

import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ScoreboardMixin {

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void disableScoreboard(CallbackInfo ci) {
        if (ModConfig.INSTANCE.disableScoreboard) {
            ci.cancel();
        }
    }
}
