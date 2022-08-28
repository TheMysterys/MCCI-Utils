package com.themysterys.mcciutils.mixin;

import com.themysterys.mcciutils.global.LocationID;
import com.themysterys.mcciutils.McciUtils;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ScoreboardObjective.class)
public class LocationMixin {

    private static String Lastlocation;

    @Inject(at= @At("HEAD"), method = "setDisplayName")
    private void onScoreboardObjectiveUpdate(Text name, CallbackInfo ci) {
        if(McciUtils.isOnMCCI()) {
            McciUtils.LOGGER.info("Scoreboard objective update: " + name.toString());
            Pattern pattern = Pattern.compile("literal\\{([A-Z ]+)}", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(name.toString());
            while (matcher.find()) {
                String location = matcher.group(1).strip();
                if (location.length() > 0 && !location.equals(Lastlocation)) {
                    McciUtils.LOGGER.info("Location changed to " + location);
                    LocationID.updateLocationID(location);
                    Lastlocation = location;
                    LocationID.sendPresence();
                    return;
                }
            }
            Lastlocation = null;
            LocationID.updateLocationID(null);
            LocationID.sendPresence();
        }
    }

}
