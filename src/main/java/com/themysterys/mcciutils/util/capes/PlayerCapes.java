package com.themysterys.mcciutils.util.capes;


import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlayerCapes {

    public static Map<String, Identifier> capes = new HashMap<>();
    public static Map<String, Boolean> hasElytra = new HashMap<>();

    public static void loadPlayerCape(GameProfile player, ReturnCapeTexture response) {
        Util.getMainWorkerExecutor().execute(() -> {
            try {
                String uuid = player.getId().toString();
                NativeImageBackedTexture nIBT;
                nIBT = getCapeFromURL("https://api.mysterybots.com/capes/" + player.getName() + ".png", uuid);
                if (nIBT == null) {
                    nIBT = getCapeFromURL("http://s.optifine.net/capes/" + player.getName() + ".png", uuid);
                }

                if (nIBT == null) {
                    capes.remove(uuid);
                    response.response(null);
                    return;
                }
                Identifier capeTexture = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("capes-" + uuid, nIBT);
                capes.put(uuid, capeTexture);
                response.response(capeTexture);

            } catch (Exception ignored) {
            }
        });
    }

    public static Identifier getCapeTexture(GameProfile player) {
        return capes.get(player.getId().toString());
    }

    public static boolean getHasElytraTexture(GameProfile player) {
        return hasElytra.getOrDefault(player.getId().toString(), false);
    }

    public static NativeImageBackedTexture getCapeFromURL(String capeStringURL, String uuid) {
        try {
            URL capeURL = new URL(capeStringURL);
            InputStream stream = capeURL.openStream();
            return getNativeImageBackedTexture(stream, uuid);
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    private static NativeImageBackedTexture getNativeImageBackedTexture(InputStream stream, String uuid) {
        NativeImage cape = null;
        try {
            cape = NativeImage.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cape != null) {
            hasElytra.put(uuid, Math.floorDiv(cape.getWidth(),cape.getHeight()) == 2);
            return new NativeImageBackedTexture(parseCape(cape));
        }

        return null;
    }

    public static NativeImage parseCape(NativeImage image) {
        int imageWidth = 64;
        int imageHeight = 32;
        int imageSrcWidth = image.getWidth();
        int srcHeight = image.getHeight();

        for (int imageSrcHeight = image.getHeight(); imageWidth < imageSrcWidth
                || imageHeight < imageSrcHeight; imageHeight *= 2) {
            imageWidth *= 2;
        }

        NativeImage imgNew = new NativeImage(imageWidth, imageHeight, true);
        for (int x = 0; x < imageSrcWidth; x++) {
            for (int y = 0; y < srcHeight; y++) {
                imgNew.setColor(x, y, image.getColor(x, y));
            }
        }
        image.close();
        return imgNew;
    }

    public interface ReturnCapeTexture {
        void response(Identifier id);
    }
}
