package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 1/20/2017.
 */
@ExprAnnotation.Expression(
        name = "VolumeOfPlayer",
        title = "Volume Of Player",
        desc = "Get the Volume of a player",
        syntax = "volume of player [named] %Number%",
        returntype = Number.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprVolumeOf extends SimpleExpression<Number> {
    Expression<Number> vBot;
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
        return true;
    }
    public Number getVolume(Event e){
        AudioPlayer player = EffLogin.audioPlayers.get(vBot.getSingle(e));
        return player.getVolume();
    }
}
