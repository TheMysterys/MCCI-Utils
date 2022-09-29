package com.themysterys.mcciutils.mixin.menu;

import com.themysterys.mcciutils.McciUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class Panorama extends Screen {

    @Mutable
    @Shadow @Final private RotatingCubeMapRenderer backgroundRenderer;
    private static long lastInteractionTime;
    private float fade;
    private boolean isPlayingMusic = false;
    private final SoundInstance music = PositionedSoundInstance.master(SoundEvents.MUSIC_CREDITS, 1.0F);

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

    @Inject(at = @At("TAIL"), method = "<init>(Z)V")
    private void onTitleScreenInit(boolean bl, CallbackInfo ci) {
        lastInteractionTime = Util.getMeasuringTimeMs();
        this.backgroundRenderer = new RotatingCubeMapRenderer(McciUtils.panorama);
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At("STORE"), ordinal = 2)
    private float onRender(float value) {
        if (Util.getMeasuringTimeMs() - lastInteractionTime > 20000) {
            if (fade == 0 && !isPlayingMusic) {
                MinecraftClient.getInstance().getSoundManager().play(music);
                isPlayingMusic = true;
            }
            // Decrease fade from 1 to 0 in steps of 0.01 but don't go below 0
            fade = Math.max(fade - 0.01F, 0);
            MinecraftClient.getInstance().getMusicTracker().stop();
        } else {
            if (isPlayingMusic) {
                isPlayingMusic = false;
                MinecraftClient.getInstance().getSoundManager().stop(music);
            }
            // Increase fade from 0 to 1 in steps of 0.01 but don't go above 1
            fade = Math.min(fade + 0.01F, 1);
        }
        return fade;
    }
}
