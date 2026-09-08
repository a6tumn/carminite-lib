package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.ICancellableEvent;
import carminite.events.api.ClientEvents;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.Music;
import org.jspecify.annotations.Nullable;

public class SelectMusicEvent extends CarminiteEvent implements ICancellableEvent {
    private @Nullable Music music;
    private final Music originalMusic;
    private final @Nullable SoundInstance playingMusic;

    public SelectMusicEvent(Music music, @Nullable SoundInstance playingMusic) {
        this.music = music;
        this.originalMusic = music;
        this.playingMusic = playingMusic;
    }

    public Music getOriginalMusic() {
        return originalMusic;
    }

    @Nullable
    public SoundInstance getPlayingMusic() {
        return playingMusic;
    }

    @Nullable
    public Music getMusic() {
        return music;
    }

    public void setMusic(@Nullable Music newMusic) {
        this.music = newMusic;
    }

    public void overrideMusic(@Nullable Music newMusic) {
        this.music = newMusic;
        this.setCanceled(true);
    }

    @Override
    public SelectMusicEvent post() {
        ClientEvents.SELECT_MUSIC.invoker().selectMusic(this);
        return this;
    }
}

