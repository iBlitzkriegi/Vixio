package me.iblitzkriegi.vixio.expressions.command;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.discordCommands.DiscordCommand;
import org.bukkit.event.Event;

public class ExprDescription extends SimplePropertyExpression<DiscordCommand, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprDescription.class, String.class,
                "description", "discordcommands")
                .setName("Description of Command")
                .setDesc("Returns the description of a command.")
                .setExample("broadcast description of \"commandname\" parsed as a discord command");
    }

    @Override
    public String convert(DiscordCommand command) {
        return command.getDescription();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "description of command";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the description of " + getExpr().toString(e, debug);
    }

}