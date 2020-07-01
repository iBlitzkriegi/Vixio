package me.iblitzkriegi.vixio.expressions.guild.invite;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Invite;
import org.bukkit.event.Event;

import java.util.List;

public class ExprRetrievedInvites extends SimpleExpression<Invite> {
    public static List<Invite> lastRetrievedInvites;

    static {
        Vixio.getInstance().registerExpression(ExprRetrievedInvites.class, Invite.class, ExpressionType.SIMPLE,
                "[the] last (grabbed|retrieved) invites")
                .setName("Retrieved Invites")
                .setDesc("Get the invites the retrieve invites effect retrieved.")
                .setExample(
                        "discord command test:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tretrieve the invites of event-guild",
                        "\t\treply with \"%last grabbed invites%\""
                );
    }

    @Override
    protected Invite[] get(Event e) {
        return lastRetrievedInvites == null ? null : lastRetrievedInvites.toArray(new Invite[lastRetrievedInvites.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Invite> getReturnType() {
        return Invite.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last (grabbed|retrieved) invites";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
