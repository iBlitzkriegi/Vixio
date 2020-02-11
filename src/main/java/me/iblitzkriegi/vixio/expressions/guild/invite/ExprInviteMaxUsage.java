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

public class ExprInviteMaxUsage extends ChangeableSimplePropertyExpression<Invite, Number> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprInviteMaxUsage.class, Number.class, "max (use[s]|usage)", "invite")
                .setName("Invite Max Usage")
                .setDesc("Get either how many times an invite can be used, or set how many times an invite can be used inside the invite creation scope.")
                .setExample(
                        "discord command rawr:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tcreate invite to event-channel with event-bot:",
                        "\t\t\tset max uses of the invite to 5",
                        "\t\t\tset {_} to the invite",
                        "\t\treply with \"%max uses of {_}%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "max use[s]";
    }

    @Override
    public Number convert(Invite invite) {
        return invite.getMaxUses();
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
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<Invite>) exprs[0]);
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (Invite invite : getExpr().getAll(e)) {
            if (!invite.isCreated()) {
                invite.setMaxUses(mode == Changer.ChangeMode.SET ? ((Number) delta[0]).intValue() : -1);
            }
        }
    }
}
