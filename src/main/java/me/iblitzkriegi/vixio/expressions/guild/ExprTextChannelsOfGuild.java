package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

public class ExprTextChannelsOfGuild extends SimpleExpression<TextChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprTextChannelsOfGuild.class, TextChannel.class, ExpressionType.SIMPLE, "text[(-| )]channel[s] of %guild%")
                .setName("Text Channels of Guild")
                .setDesc("Get all of the text channels in a guild.")
                .setExample(
                        "on guild message receive:",
                        "\tset {channels::*} to text channels of event-guild",
                        "\tloop {channels::*}:",
                        "\t\tbroadcast \"%name of loop-value%\""
                );
    }
    private Expression<Guild> guild;
    @Override
    protected TextChannel[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return null;
        }

        return guild.getTextChannels().toArray(new TextChannel[guild.getTextChannels().size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends TextChannel> getReturnType() {
        return TextChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "text channels of " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guild = (Expression<Guild>) exprs[0];
        return true;
    }
}
