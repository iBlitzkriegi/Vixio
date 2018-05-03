package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;

public class ExprUrl extends SimplePropertyExpression<AudioTrack, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprUrl.class, String.class,
                "ur(i|l)", "tracks")
                .setName("Url of track")
                .setDesc("Get a track's url.")
                .setExample("broadcast \"%url of track event-bot is playing%\"");
    }

    @Override
    protected String getPropertyName() {
        return "url";
    }

    @Override
    public String convert(AudioTrack audioTrack) {
        return audioTrack.getInfo().uri;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
