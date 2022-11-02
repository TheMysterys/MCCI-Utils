package com.themysterys.mcciutils.mixin.ingame;

import com.mojang.authlib.GameProfile;
import com.themysterys.mcciutils.util.capes.PlayerCapes;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerListEntry.class)
public class CapesMixin {

    @Shadow
    @Final
    private GameProfile profile;
    @Shadow
    private boolean texturesLoaded;

    private boolean loaded = false;

    @Inject(method = "loadTextures", at = @At("HEAD"))
    private void loadTextures(CallbackInfo ci) {
        if (loaded) return;
        if (profile.getName().contains("§") || profile.getName().matches("\\d\\d")) {
            loaded = true;
            return;
        }
        if (!texturesLoaded) {
            loaded = true;
            PlayerCapes.loadPlayerCape(profile, id -> {});
        }

    }

    @Inject(method = "getCapeTexture", at = @At("TAIL"), cancellable = true)
    private void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        Identifier id = PlayerCapes.getCapeTexture(profile);
        if (id != null) {
            cir.setReturnValue(id);
        }
    }
}
