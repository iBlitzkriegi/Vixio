package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprChannelsOf extends SimpleExpression<Channel> {
    static {
        Vixio.getInstance().registerExpression(ExprChannelsOf.class, Channel.class, ExpressionType.SIMPLE,
                "channel[s] of %guild/category%")
                .setName("Channels of")
                .setDesc("Get all of the Channels of a Guild/Category.")
                .setExample(
                        "on guild message receive:",
                        "\tset {channels::*} to channels of event-guild",
                        "\tloop {channels::*}:",
                        "\t\tbroadcast \"%name of loop-value%\""
                );
    }

    private Expression<Object> object;

    @Override
    protected Channel[] get(Event e) {
        Object object = this.object.getSingle(e);
        if (object == null) {
            return null;
        }

        if (object instanceof Guild) {
            Guild guild = (Guild) object;
            List<Channel> channels = new ArrayList<>();
            channels.addAll(guild.getTextChannels());
            channels.addAll(guild.getVoiceChannels());

            return channels.toArray(new Channel[channels.size()]);
        } else if (object instanceof Category) {
            List<Channel> channels = ((Category) object).getChannels();
            return channels.toArray(new Channel[channels.size()]);
        }

        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Channel> getReturnType() {
        return Channel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "channels of " + object.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        return true;
    }
}
