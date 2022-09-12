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

    private static String LastLocation;

    @Inject(at= @At("HEAD"), method = "setDisplayName")
    private void onScoreboardObjectiveUpdate(Text name, CallbackInfo ci) {
        if(McciUtils.isOnMCCI()) {
            Pattern pattern = Pattern.compile("MCCI: (.+)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(name.getString());
            while (matcher.find()) {
                String location = matcher.group(1).strip();
                if (location.length() > 0 && !location.equals(LastLocation)) {
                    LocationID.updateLocationID(location);
                    LastLocation = location;
                    LocationID.sendPresence();
                    return;
                }
            }
            LocationID.sendPresence();
        }
    }

}
