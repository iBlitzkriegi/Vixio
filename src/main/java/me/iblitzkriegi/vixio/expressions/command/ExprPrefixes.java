package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprPrefixes extends SimpleExpression<String> {

    static {
        Vixio.getInstance().registerExpression(ExprPrefixes.class, String.class, ExpressionType.PROPERTY,
                "prefixes of %discordcommands%", "%discordcommands%'[s] prefixes")
                .setName("Prefixes of command")
                .setDesc("Returns the prefixes of a command")
                .setExample("broadcast prefixes of \"commandname\" parsed as a discord command");
    }

    private Expression<DiscordCommand> cmds;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        cmds = (Expression<DiscordCommand>) exprs[0];
        return true;
    }

    @Override
    protected String[] get(Event e) {
        DiscordCommand[] cmds = this.cmds.getAll(e);
        if (cmds == null) {
            return null;
        }

        List<String> prefixes = new ArrayList<>();
        for (DiscordCommand cmd : cmds) {
            if (cmd.getPrefixes() != null) {
                prefixes.addAll(cmd.getPrefixes());
            }
        }

        return prefixes.isEmpty() ? null : prefixes.toArray(new String[prefixes.size()]);
    }

    @Override
    public boolean isSingle() {
        return cmds.isSingle();
    }

    @Override
    public Class<String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "prefixes of " + cmds.toString(e, debug);
    }

}
