package com.themysterys.mcciutils.mixin.ingame;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.themysterys.mcciutils.util.capes.PlayerCapes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraMixin {

    @WrapOperation(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;canRenderCapeTexture()Z")
    )
    public boolean toggleCustomElytra(AbstractClientPlayerEntity player, Operation<Boolean> ignored) {
        return PlayerCapes.getHasElytraTexture(player.getGameProfile());
    }
}
