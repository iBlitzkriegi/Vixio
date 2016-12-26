package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/17/2016.
 */
@ExprAnnotation.Expression(returntype = AudioTrack.class, type = ExpressionType.SIMPLE, syntax = "track [audio] player %string% is playing]")
public class ExprPlayerTrack extends SimpleExpression<AudioTrack> {
    private static Expression<String> vBot;
    @Override
    protected AudioTrack[] get(Event e) {
        return new AudioTrack[]{playingTrack(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends AudioTrack> getReturnType() {
        return AudioTrack.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        return true;
    }
    private static AudioTrack playingTrack(Event e){
        TrackScheduler trackScheduler = EffLogin.trackSchedulers.get(vBot.getSingle(e));
        return trackScheduler.getPlayer().getPlayingTrack();
    }
}
