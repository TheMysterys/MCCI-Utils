package com.themysterys.mcciutils.util.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Sounds {

    // Sound IDs
    private static final Identifier FRIEND_SOUND_ID = new Identifier("mcciutils:friend");
    private static final Identifier MENTION_SOUND_ID = new Identifier("mcciutils:mention");
    private static final Identifier UNLOCK_SOUND_ID = new Identifier("mcciutils:achievement");

    // Sound Events
    public static SoundEvent FRIEND_SOUND = new SoundEvent(FRIEND_SOUND_ID);
    public static SoundEvent MENTION_SOUND = new SoundEvent(MENTION_SOUND_ID);
    public static SoundEvent UNLOCK_SOUND = new SoundEvent(UNLOCK_SOUND_ID);

}
