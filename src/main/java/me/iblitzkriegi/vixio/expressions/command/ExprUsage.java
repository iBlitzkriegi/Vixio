package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import org.bukkit.event.Event;

public class ExprUsage extends SimplePropertyExpression<DiscordCommand, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprUsage.class, String.class,
                "usage", "discordcommands")
                .setName("Usage of Command")
                .setDesc("Returns the usage of a command.")
                .setExample("broadcast usage of \"commandname\" parsed as a discord command");
    }

    @Override
    public String convert(DiscordCommand command) {
        return command.getUsage();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "usage of command";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the usage of " + getExpr().toString(e, debug);
    }

}