package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Timespan;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;

public class ExprPosition extends SimplePropertyExpression<AudioTrack, Timespan> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprPosition.class, Timespan.class,
                "position", "tracks")
                .setName("Position of track")
                .setDesc("Get the position a track is at. This is the current time it is at")
                .setExample("broadcast \"%position of track event-bot is playing%\"");
    }

    @Override
    protected String getPropertyName() {
        return "position";
    }

    @Override
    public Timespan convert(AudioTrack audioTrack) {
        return Timespan.fromTicks_i((audioTrack.getPosition() / 1000) * 20);
    }

    @Override
    public Class<? extends Timespan> getReturnType() {
        return Timespan.class;
    }
}
