package me.iblitzkriegi.vixio.expressions;

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
 * Created by Blitz on 12/17/2016.
 */
@ExprAnnotation.Expression(
        name = "BotIsStreaming",
        title = "Bot is Streaming",
        desc = "Check if one of your Bots is streaming",
        syntax = "bot %string% is streaming track",
        returntype = Boolean.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprIsPlayingStream extends SimpleExpression<Boolean> {
    private static Expression<String> vBot;
    @Override
    protected Boolean[] get(Event e) {
        return new Boolean[]{playingTrack(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
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
    private static Boolean playingTrack(Event e){
        AudioPlayer player = EffLogin.audioPlayers.get(vBot.getSingle(e));
        return player.getPlayingTrack().getInfo().isStream;
    }
}
