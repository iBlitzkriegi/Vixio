package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Timespan;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;

public class ExprDuration extends SimplePropertyExpression<AudioTrack, Timespan> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprDuration.class, Timespan.class,
                "duration", "tracks")
                .setName("Duration of track")
                .setDesc("Get the duration of a track. This is how long it is.")
                .setExample("broadcast \"%duration of track event-bot is playing%\"");
    }

    @Override
    protected String getPropertyName() {
        return "duration";
    }

    @Override
    public Timespan convert(AudioTrack audioTrack) {
        return Timespan.fromTicks_i((audioTrack.getDuration() / 1000) * 20);
    }

    @Override
    public Class<? extends Timespan> getReturnType() {
        return Timespan.class;
    }
}
