package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.util.McciToast;
import com.themysterys.mcciutils.util.updates.UpdateChecker;
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
        UpdateChecker updateChecker = new UpdateChecker();
        if (updateChecker.isUpdateAvailable) {
            ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
            McciToast.add(toastManager, McciToast.Type.UPDATE, Text.literal("MCCI Utils %s is available!".formatted(updateChecker.latestVersion)));
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void checkComplete(CallbackInfo ci) {

    }
}
