package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import org.bukkit.event.Event;


public class ExprAliases extends PropertyExpression<DiscordCommand, String> implements EasyMultiple<DiscordCommand, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprAliases.class, String.class,
                "[<usable>] aliases", "discordcommands")
                .setName("Aliases of command")
                .setDesc("Returns the aliases of a command")
                .setExample("broadcast aliases of \"commandname\" parsed as a discord command");
    }

    private boolean usable;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<DiscordCommand>) exprs[0]);
        usable = parseResult.regexes.size() == 1;
        return true;
    }

    @Override
    public String[] get(Event e, DiscordCommand[] commands) {
        if (usable) {
            return convert(getReturnType(), getExpr().getAll(e), c -> c.getUsableAliases().toArray(new String[c.getUsableAliases().size()])
            );
        } else {
            return convert(getReturnType(), getExpr().getAll(e), c ->
                    c.getAliases() == null ? null : c.getAliases().toArray(new String[c.getAliases().size()])
            );
        }
    }

    @Override
    public Class<String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "aliases of " + getExpr().toString(e, debug);
    }

}

