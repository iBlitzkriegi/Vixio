package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;

public class ExprIdentifier extends SimplePropertyExpression<AudioTrack, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprIdentifier.class, String.class,
                "identifier", "tracks")
                .setName("Identifier of Track")
                .setDesc("Get a track's identifier, this is the unique ID given to it by the hosting site.")
                .setExample("broadcast \"%identifier of track event-bot is playing%\"");
    }

    @Override
    protected String getPropertyName() {
        return "identifier";
    }

    @Override
    public String convert(AudioTrack audioTrack) {
        return audioTrack.getIdentifier();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
