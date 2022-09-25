package com.themysterys.mcciutils.util.panoramas;

import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.util.Identifier;

import java.util.Random;

public class Panoramas {

    CubeMapRenderer panorama;


    public Panoramas() {
        int SIZE = PanoramaLocation.values().length;
        Random random = new Random();
        int index = random.nextInt(SIZE);
        panorama = PanoramaLocation.values()[index].panorama;
    }

    public CubeMapRenderer getPanorama() {
        return panorama;
    }

    private enum PanoramaLocation {
        MAIN_ISLAND(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/main-island/panorama"))),
        TGTTOS(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/tgttos/panorama")));

        final CubeMapRenderer panorama;
        PanoramaLocation(CubeMapRenderer panorama) {
            this.panorama = panorama;
        }
    }
}
