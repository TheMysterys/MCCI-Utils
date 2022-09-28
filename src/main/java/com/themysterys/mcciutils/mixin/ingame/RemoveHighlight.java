package com.themysterys.mcciutils.mixin.ingame;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.themysterys.mcciutils.util.config.ModConfig;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HandledScreen.class)
public class RemoveHighlight {

    @Shadow @Nullable protected Slot focusedSlot;

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawSlotHighlight(Lnet/minecraft/client/util/math/MatrixStack;III)V"))
    private boolean removeHighlight(MatrixStack matrices, int x, int y, int z) {
        if (this.focusedSlot != null) {
            return !ModConfig.INSTANCE.hideSlotHighlight || this.focusedSlot.hasStack();
        }
        return true;
    }
}
