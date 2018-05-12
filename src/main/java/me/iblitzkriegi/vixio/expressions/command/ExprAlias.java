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

public class ExprAlias extends SimpleExpression<String> {

    static {
		Vixio.getInstance().registerExpression(ExprAlias.class, String.class, ExpressionType.SIMPLE, "[the] used alias")
                .setName("Alias")
                .setDesc("Returns the used alias in a command")
                .setExample("broadcast the used alias");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(DiscordCommandEvent.class)) {
            Skript.error("You can only get the used alias in a discord command");
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event e) {
        return new String[]{((DiscordCommandEvent) e).getUsedAlias()};
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
		return "the used alias";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}