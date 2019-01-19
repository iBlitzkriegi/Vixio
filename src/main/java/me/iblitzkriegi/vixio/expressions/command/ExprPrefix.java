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
import org.bukkit.event.Event;

public class ExprPrefix extends SimpleExpression<String> {

    static {
        Vixio.getInstance().registerExpression(ExprPrefix.class, String.class, ExpressionType.SIMPLE, "[the] used prefix")
                .setName("Used Prefix")
                .setDesc("Returns the used prefix in a command")
                .setExample("broadcast the used prefix");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(DiscordCommandEvent.class)) {
            Skript.error("You can only get the used prefix in a discord command");
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event e) {
        return new String[]{((DiscordCommandEvent) e).getPrefix()};
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the used prefix";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}