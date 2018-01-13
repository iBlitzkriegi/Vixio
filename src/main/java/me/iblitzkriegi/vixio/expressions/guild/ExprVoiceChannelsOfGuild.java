package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

public class ExprVoiceChannelsOfGuild extends SimpleExpression<VoiceChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprVoiceChannelsOfGuild.class, VoiceChannel.class, ExpressionType.SIMPLE, "voice[(-| )]channel[s] of %guild%")
                .setName("Voice Channels of Guild")
                .setDesc("Get all of the voices channels in a guild.")
                .setExample(
                        "on guild message receive:",
                        "\tset {channels::*} to voice channels of event-guild",
                        "\tloop {channels::*}:",
                        "\t\tbroadcast \"%name of loop-value%\""
                );
    }
    private Expression<Guild> guild;
    @Override
    protected VoiceChannel[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return null;
        }

        return guild.getVoiceChannels().toArray(new VoiceChannel[guild.getVoiceChannels().size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends VoiceChannel> getReturnType() {
        return VoiceChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "voice channels of " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guild = (Expression<Guild>) exprs[0];
        return true;
    }
}
