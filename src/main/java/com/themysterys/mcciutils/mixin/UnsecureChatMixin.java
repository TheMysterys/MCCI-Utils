package com.themysterys.mcciutils.mixin;

import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Deque;

@Mixin(ToastManager.class)
public class UnsecureChatMixin {


    @Shadow
    @Final
    private Deque<Toast> toastQueue;

    /**
     * @author TheMysterys
     * @reason Prevent UNSECURE_SERVER_WARNING from showing
     */
    @Overwrite
    public void add(Toast toast) {
        if (toast instanceof SystemToast systemToast) {
            if (systemToast.getType() != SystemToast.Type.UNSECURE_SERVER_WARNING) {
                toastQueue.add(toast);
            }
        } else {
            toastQueue.add(toast);
        }
    }

}
