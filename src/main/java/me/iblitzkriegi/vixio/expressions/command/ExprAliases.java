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

public class ExprAliases extends SimpleExpression<String> {

    static {
        Vixio.getInstance().registerExpression(ExprAliases.class, String.class, ExpressionType.PROPERTY,
                "[<usable>] aliases of %discordcommands%", "%discordcommands%'[s] [<usable>] aliases")
                .setName("Aliases of command")
                .setDesc("Returns the aliases of a command")
                .setExample("broadcast aliases of \"commandname\" parsed as a discord command");
    }

    private Expression<DiscordCommand> cmds;
    private boolean usable;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        cmds = (Expression<DiscordCommand>) exprs[0];
        usable = parseResult.regexes.size() == 1;
        return true;
    }

    @Override
    protected String[] get(Event e) {
        DiscordCommand[] cmds = this.cmds.getAll(e);
        if (cmds == null)
            return null;

        List<String> aliases = new ArrayList<>();
        for (DiscordCommand cmd : cmds) {
            if (usable) {
                if (cmd.getUsableAliases() != null)
                    aliases.addAll(cmd.getUsableAliases());
            } else {
                if (cmd.getAliases() != null)
                    aliases.addAll(cmd.getAliases());
            }
        }

        return aliases.isEmpty() ? null : aliases.toArray(new String[aliases.size()]);
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
        return "aliases of " + cmds.toString(e, debug);
    }

}
