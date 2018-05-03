package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;

public class ExprTrackAuthor extends SimplePropertyExpression<AudioTrack, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprTrackAuthor.class, String.class,
                "author", "tracks")
                .setName("Author of track")
                .setDesc("Get a track's author. This is the creator of the source.")
                .setExample("broadcast \"%author of track event-bot is playing%\"");
    }

    @Override
    protected String getPropertyName() {
        return "author";
    }

    @Override
    public String convert(AudioTrack audioTrack) {
        return audioTrack.getInfo().author;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
