package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Timespan;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import org.bukkit.event.Event;

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

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)) {
            return new Class[]{Timespan.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        long position = mode == Changer.ChangeMode.SET ? (((Timespan) delta[0]).getTicks_i() / 20) * 1000 : 0;
        for (AudioTrack track : getExpr().getAll(e)) {
            track.setPosition(position);
        }
    }

}
