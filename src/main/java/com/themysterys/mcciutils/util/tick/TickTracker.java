package com.themysterys.mcciutils.util.tick;

import com.themysterys.mcciutils.Global.LocationListener;

public class TickTracker {

    public static int tickNo = 0;

    public static void onTick(){
        tick();
    }

    private static void tick() {
        LocationListener.listenWorld();
        tickNo = tickNo + 1;
    }
}
