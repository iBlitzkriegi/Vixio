package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.util.VixioAudioHandlers.getGuildAudioPlayer;

/**
 * Created by Blitz on 1/20/2017.
 */
@ExprAnnotation.Expression(
        name = "VolumeOfPlayer",
        title = "Volume Of Player",
        desc = "Get the Volume of a player",
        syntax = "volume of player [named] %string% in guild %string%",
        returntype = Number.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprVolumeOf extends SimpleExpression<Number> {
    Expression<Number> vBot;
    Expression<String> vGuild;
    @Override
    protected Number[] get(Event e) {
        return new Number[]{getVolume(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<Number>) expressions[0];
        vGuild = (Expression<String>) expressions[1];
        return true;
    }
    public Number getVolume(Event e){
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            return musicManager.player.getVolume();
        }else{
            Skript.warning("Could not find Guild via that ID");
        }
        return null;
    }
}
