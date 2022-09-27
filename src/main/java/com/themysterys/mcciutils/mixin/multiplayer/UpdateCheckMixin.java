package com.themysterys.mcciutils.mixin.multiplayer;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.McciToast;
import com.themysterys.mcciutils.util.api.UpdateCheckerAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class UpdateCheckMixin {

    @Inject(at = @At("TAIL"), method = "init")
    public void checkUpdate(CallbackInfo ci) {
        if (McciUtils.hasShownUpdateToast) return;
        UpdateCheckerAPI updateCheckerAPI = new UpdateCheckerAPI();
        if (updateCheckerAPI.isUpdateAvailable) {
            ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
            McciToast.add(toastManager, McciToast.Type.UPDATE, Text.literal("MCCI Utils %s is available!".formatted(updateCheckerAPI.latestVersion)));
            McciUtils.hasShownUpdateToast = true;
        }
    }

}
