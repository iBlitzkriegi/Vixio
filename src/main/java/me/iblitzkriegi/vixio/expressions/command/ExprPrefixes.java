package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.discordCommands.DiscordCommand;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import org.bukkit.event.Event;

public class ExprPrefixes extends PropertyExpression<DiscordCommand, String> implements EasyMultiple<DiscordCommand, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprPrefixes.class, String.class,
                "prefixes", "discordcommands")
                .setName("Prefixes of command")
                .setDesc("Returns the prefixes of a command")
                .setExample("broadcast prefixes of \"commandname\" parsed as a discord command");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<DiscordCommand>) exprs[0]);
        return true;
    }

    @Override
    public String[] get(Event e, DiscordCommand[] commands) {
        return convert(getReturnType(), getExpr().getAll(e), c -> c.getPrefixes().toArray(new String[c.getPrefixes().size()]));
    }

    @Override
    public Class<String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "prefixes of " + getExpr().toString(e, debug);
    }

}
