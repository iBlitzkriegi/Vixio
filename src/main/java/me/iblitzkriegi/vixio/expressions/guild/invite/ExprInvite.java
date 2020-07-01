package me.iblitzkriegi.vixio.expressions.guild.invite;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.scopes.ScopeMakeInvite;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import me.iblitzkriegi.vixio.util.wrapper.Invite;
import org.bukkit.event.Event;

public class ExprInvite extends SimpleExpression<Invite> {

    static {
        Vixio.getInstance().registerExpression(ExprInvite.class, Invite.class, ExpressionType.SIMPLE,
                "[(the|an|[a] new)] invite")
                .setName("New/Current Invite")
                .setDesc("If it isn't inside an invite creation scope, this expression returns a new invite. " +
                        "If it is inside an invite creation scope, it returns the invite that belongs to that scope.")
                .setExample(
                        "discord command rawr:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tcreate invite to event-channel with event-bot:",
                        "\t\t\tset max uses of the invite to 5",
                        "\t\t\tset {_} to the invite",
                        "\t\treply with \"%creation date of of {_}%\""
                );
    }

    private boolean scope = false;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        scope = EffectSection.isCurrentSection(ScopeMakeInvite.class);
        return true;
    }

    @Override
    protected Invite[] get(Event e) {
        return new Invite[]{
                scope ? ScopeMakeInvite.lastInvite : new Invite()
        };
    }

    @Override
    public Class<? extends Invite> getReturnType() {
        return Invite.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the invite";
    }

    @Override
    public boolean isSingle() {
        return true;
    }
}
