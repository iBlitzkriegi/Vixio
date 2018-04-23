package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.member.EvtNickChange;
import org.bukkit.event.Event;

public class ExprNick extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprNick.class, String.class, ExpressionType.SIMPLE,
                "[the] prev[ious] nick[name]", "[the] new nick[name]")
                .setName("New and Previous Nicknames")
                .setDesc("Get the new or previous nickname from the nick change event.")
                .setExample(
                        "on nickname change:",
                        "\tbroadcast \"%event-member% has changed their nick from %prev nick% to %new nick%\"\n"
                );
    }

    private boolean not;

    @Override
    protected String[] get(Event e) {
        if (not) {
            return new String[]{((EvtNickChange.MemberNickChange) e).getJDAEvent().getPrevNick()};
        }
        return new String[]{((EvtNickChange.MemberNickChange) e).getJDAEvent().getNewNick()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the " + (not ? "previous" : "new") + " nickname";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (ScriptLoader.isCurrentEvent(EvtNickChange.MemberNickChange.class)) {
            not = matchedPattern == 0;
            return true;
        }
        Skript.error("This expression may only be used inside of the nick change event");
        return false;
    }
}
