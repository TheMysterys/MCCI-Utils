package com.themysterys.mcciutils.util.chat;

import com.themysterys.mcciutils.util.websockets.ModUsers;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModBadgeIcon {
    public static Text modBadge(String name) {
        return Text.literal("\uEFEF").setStyle(Style.EMPTY.withFont(new Identifier("mcciutils:icon"))).formatted(ModUsers.getRankColor(name));
    }
}
