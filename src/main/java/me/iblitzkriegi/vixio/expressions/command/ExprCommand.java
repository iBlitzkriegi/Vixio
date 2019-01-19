package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.commands.DiscordCommandEvent;
import org.bukkit.event.Event;

public class ExprCommand extends SimpleExpression<DiscordCommand> {

    static {
        Vixio.getInstance().registerExpression(ExprCommand.class, DiscordCommand.class, ExpressionType.SIMPLE, "[the] used command")
                .setName("Used Command")
                .setDesc("Returns the used command in a command")
                .setExample("broadcast aliases of the used command");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(DiscordCommandEvent.class)) {
            Skript.error("You can only get the used command in a discord command");
            return false;
        }
        return true;
    }

    @Override
    protected DiscordCommand[] get(Event e) {
        return new DiscordCommand[]{((DiscordCommandEvent) e).getCommand()};
    }

    @Override
    public Class<? extends DiscordCommand> getReturnType() {
        return DiscordCommand.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the used command";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}