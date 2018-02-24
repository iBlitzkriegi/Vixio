package me.iblitzkriegi.vixio.changers;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import org.bukkit.event.Event;

public abstract class VixioChanger<T> implements Changer<T> {

    @Override
    public final Class<?>[] acceptChange(ChangeMode mode) {
        if (!EffChange.isParsing(null)) {
            return null;
        }
        return acceptChange(mode, true);
    }

    @Override
    public final void change(T[] what, Object[] delta, ChangeMode mode) {
        if (EffChange.currentBot != null) {
            change(what, delta, EffChange.currentBot, mode);
        }
    }

    public abstract Class<?>[] acceptChange(ChangeMode mode, boolean vixioChanger);

    public abstract void change(T[] what, Object[] delta, Bot bot, Changer.ChangeMode mode);

}
