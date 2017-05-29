package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.util.VixioAudioHandlers.getGuildAudioPlayer;

/**
 * Created by Blitz on 12/17/2016.
 */
@ExprAnnotation.Expression(
        name = "TrackPlayerIsPlaying",
        title = "Track Player Is Playing",
        desc = "Get the Track one of your player's is playing",
        syntax = "track [audio] player %string% is playing in guild [with id] %string%",
        returntype = AudioTrack.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprPlayerTrack extends SimpleExpression<AudioTrack> {
    private static Expression<String> vBot;
    private static Expression<String> vGuild;
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
        vGuild = (Expression<String>) expr[1];
        return true;
    }
    private static AudioTrack playingTrack(Event e){
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            return musicManager.player.getPlayingTrack();
        }else{
            Skript.warning("Could not find Guild via that ID");
        }
        return null;
    }
}
