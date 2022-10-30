package com.themysterys.mcciutils.mixin.menu;

import com.themysterys.mcciutils.McciUtils;
import com.themysterys.mcciutils.util.McciToast;
import com.themysterys.mcciutils.util.api.UpdateCheckerAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class UpdateCheckMixin {

    @Shadow @Nullable private String splashText;

    @Inject(at = @At("TAIL"), method = "init")
    public void checkUpdate(CallbackInfo ci) {
        if (!(MinecraftClient.getInstance().currentScreen instanceof TitleScreen)) return;
        new Thread(() -> {
            try {
                UpdateCheckerAPI updateCheckerAPI = new UpdateCheckerAPI();
                if (updateCheckerAPI.isUpdateAvailable) {
                    this.splashText = "MCCI Utils %s is available!".formatted(updateCheckerAPI.latestVersion);
                    Thread.sleep(3000);
                    if (McciUtils.hasShownUpdateToast) return;
                    ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
                    McciToast.add(toastManager, McciToast.Type.UPDATE, Text.literal("MCCI Utils %s is available!".formatted(updateCheckerAPI.latestVersion)));
                    McciUtils.hasShownUpdateToast = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
