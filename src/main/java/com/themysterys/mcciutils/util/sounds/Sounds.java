package com.themysterys.mcciutils.util.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds {

    // Sound IDs
    private static final Identifier FRIEND_SOUND_ID = new Identifier("mcc:19");
    private static final Identifier MENTION_SOUND_ID = new Identifier("mcc:1p");

    // Sound Events
    public static SoundEvent FRIEND_SOUND = new SoundEvent(FRIEND_SOUND_ID);
    public static SoundEvent MENTION_SOUND = new SoundEvent(MENTION_SOUND_ID);

}
