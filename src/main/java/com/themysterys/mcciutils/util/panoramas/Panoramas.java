package com.themysterys.mcciutils.util.panoramas;

import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.util.Identifier;

import java.util.*;

public class Panoramas {

    CubeMapRenderer panorama;


    public Panoramas() {
        panorama = PanoramaLocation.randomLocation();
    }

    public CubeMapRenderer getPanorama() {
        return panorama;
    }

    private enum PanoramaLocation {
        MAIN_ISLAND(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/main-island/panorama")), 20),
        TGTTOS(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/tgttos/panorama")), 20),
        HITW(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/hitw/panorama")), 20),
        BB(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/bb/panorama")), 20),
        SB(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/sb/panorama")), 20),
        YELLOW(new CubeMapRenderer(new Identifier("mcciutils", "textures/gui/panoramas/yellow/panorama")), 1);

        final CubeMapRenderer panorama;
        final int weight;

        PanoramaLocation(CubeMapRenderer panorama, int weight) {
            this.panorama = panorama;
            this.weight = weight;
        }

        private static final Random RANDOM = new Random();

        public static CubeMapRenderer randomLocation()  {
            List<PanoramaLocation> VALUES = new ArrayList<>();
            for (PanoramaLocation value : values()) {
                for (int i = 0; i < value.weight; i++) {
                    VALUES.add(value);
                }
            }
            int randomNum = RANDOM.nextInt(VALUES.size());
            return VALUES.get(randomNum).panorama;
        }
    }
}
