package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.member.EvtChannelSwitch;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.bukkit.event.Event;

public class ExprChannel extends SimpleExpression<GuildChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprChannel.class, GuildChannel.class, ExpressionType.SIMPLE,
                "[the] (prev[ious]|old) [voice] channel", "[the] new [voice] channel")
                .setName("New and Previous Channel")
                .setDesc("Get the new channel or previous channel for the member channel switch event.")
                .setExample(
                        "on member switch voice channel:\n",
                        "\tbroadcast \"%event-user% left %old channel% and joined %new channel%\""
                );
    }

    private boolean old;

    @Override
    protected GuildChannel[] get(Event e) {
        if (old) {
            return new GuildChannel[]{((EvtChannelSwitch.MoveVoiceEvent) e).getJDAEvent().getChannelLeft()};
        }
        return new GuildChannel[]{((EvtChannelSwitch.MoveVoiceEvent) e).getJDAEvent().getChannelJoined()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends GuildChannel> getReturnType() {
        return GuildChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the " + (old ? "old" : "new") + " channel";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (ScriptLoader.isCurrentEvent(EvtChannelSwitch.MoveVoiceEvent.class)) {
            old = matchedPattern == 0;
            return true;
        }
        Skript.error("This expression may only be used inside the member switch channel event.");
        return false;
    }
}
