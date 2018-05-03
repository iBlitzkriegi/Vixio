package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommandEvent;
import net.dv8tion.jda.core.entities.ChannelType;
import org.bukkit.event.Event;

public class ExprRecipient extends SimpleExpression<ChannelType> {

    static {
        Vixio.getInstance().registerExpression(ExprRecipient.class, ChannelType.class, ExpressionType.SIMPLE, "[the] command recipient")
                .setName("Command Recipient")
                .setDesc("Returns the type of channel a command was sent to")
                .setExample("broadcast the command recipient");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(DiscordCommandEvent.class)) {
            Skript.error("You can only get the command recipient in a discord command");
            return false;
        }
        return true;
    }

    @Override
    protected ChannelType[] get(Event e) {
        return new ChannelType[]{((DiscordCommandEvent) e).getChannel().getType()};
    }

    @Override
    public Class<? extends ChannelType> getReturnType() {
        return ChannelType.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the command recipient";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}