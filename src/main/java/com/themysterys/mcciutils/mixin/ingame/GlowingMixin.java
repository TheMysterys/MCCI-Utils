package com.themysterys.mcciutils.mixin.ingame;

import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class GlowingMixin {

    @Inject(at = @At(value = "TAIL"), method = "hasOutline", cancellable = true)
    private void disableGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.INSTANCE.disableGlowing) {
            cir.setReturnValue(false);
        }
    }
}
