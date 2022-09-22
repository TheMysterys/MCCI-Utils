package com.themysterys.mcciutils.mixin.menu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class Panorama extends Screen {

    private long lastInteractionTime;
    private float fade;
    private boolean isPlayingMusic = false;

    protected Panorama(Text title) {
        super(title);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        lastInteractionTime = Util.getMeasuringTimeMs();
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        lastInteractionTime = Util.getMeasuringTimeMs();
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    public void init(CallbackInfo callbackInfo) {
        lastInteractionTime = Util.getMeasuringTimeMs();
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At("STORE"), ordinal = 2)
    private float onRender(float value) {
        if (Util.getMeasuringTimeMs() - lastInteractionTime > 20000) {
            if (fade == 0 && !isPlayingMusic) {
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.MUSIC_CREDITS, 1.0F));
                isPlayingMusic = true;
            }
            // Decrease fade from 1 to 0 in steps of 0.01 but don't go below 0
            fade = Math.max(fade - 0.01F, 0);
            return fade;
        } else {
            if (isPlayingMusic) {
                isPlayingMusic = false;
                MinecraftClient.getInstance().getSoundManager().stopAll();
            }
            // Increase fade from 0 to 1 in steps of 0.01 but don't go above 1
            fade = Math.min(fade + 0.01F, 1);
            return fade;
        }
    }
}
