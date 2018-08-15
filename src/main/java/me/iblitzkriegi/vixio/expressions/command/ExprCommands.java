package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.commands.DiscordCommandFactory;
import org.bukkit.event.Event;

import java.util.Collection;

public class ExprCommands extends SimpleExpression<DiscordCommand> {

    static {
        Vixio.getInstance().registerExpression(ExprCommands.class, DiscordCommand.class, ExpressionType.SIMPLE, "[all] discord commands")
                .setName("All Commands")
                .setDesc("Returns all registered discord commands")
                .setExample(
                        "loop all discord commands:",
                        "\tbroadcast description of loop-value"
                );
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected DiscordCommand[] get(Event e) {
        Collection<DiscordCommand> cmds = DiscordCommandFactory.getInstance().getCommands();
        return cmds.toArray(new DiscordCommand[cmds.size()]);
    }

    @Override
    public Class<? extends DiscordCommand> getReturnType() {
        return DiscordCommand.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "all discord commands";
    }

    @Override
    public boolean isSingle() {
        return false;
    }

}
