package com.themysterys.mcciutils.mixin.ingame;

import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossbarMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void disableBossbar(CallbackInfo ci) {
        if (ModConfig.INSTANCE.disableBossbar) {
            ci.cancel();
        }
    }
}
