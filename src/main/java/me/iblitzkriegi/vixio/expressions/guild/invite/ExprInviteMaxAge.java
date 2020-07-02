package me.iblitzkriegi.vixio.expressions.guild.invite;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Invite;
import org.bukkit.event.Event;

public class ExprInviteMaxAge extends ChangeableSimplePropertyExpression<Invite, Number> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprInviteMaxAge.class, Number.class,
                "max age", "invite")
                .setName("Max Age")
                .setDesc("Set how long an invite that is being created in the invite creation scope should last. (In seconds)." +
                        "This defaults to 86400 seconds (24 Hours.). Set it to 0 if you want it to never expire. This number cannot be negative")
                .setExample(
                        "discord command invite:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tcreate invite to event-channel:",
                        "\t\t\tset the max usage of the invite to 1",
                        "\t\t\tset the max age of the invite to 36",
                        "\t\t\tset {_} to the invite",
                        "\t\treply with \"%max age of {_}%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "max age";
    }

    @Override
    public Number convert(Invite invite) {
        return invite.getMaxAge();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Number.class};
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<Invite>) exprs[0]);
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (Invite invite : getExpr().getAll(e)) {
            if (!invite.isCreated()) {
                invite.setMaxAge(mode == Changer.ChangeMode.SET ? ((Number) delta[0]).intValue() : 0);
            }
        }
    }
}
