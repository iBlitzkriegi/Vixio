package me.iblitzkriegi.vixio.changers;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.util.SimpleExpression;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import org.bukkit.event.Event;

public abstract class ChangeableSimpleExpression<T> extends SimpleExpression<T> {

    @Override
    public final void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Skript.exception(new UnsupportedOperationException("This expression can only be changed using Vixio's changer effects. " +
                "Please report how you got this error to show at https://github.com/iBlitzkriegi/Vixio/issues"));
    }

    @Override
    public final Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (!EffChange.isParsing(this, shouldError())) {
            return null;
        }
        return acceptChange(mode, true);
    }

    public boolean shouldError() {
        return true;
    }

    public abstract Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger);

    public abstract void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode);

}
