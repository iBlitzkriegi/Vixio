package me.iblitzkriegi.vixio.changers;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.util.wrapper.Bot;

public abstract class VixioChanger<T> implements Changer<T> {

    @Override
    public final Class<?>[] acceptChange(ChangeMode mode) {
        if (!EffChange.isParsing(null, true)) {
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

    @SuppressWarnings("unchecked")
    public VixioChanger documentation(String title, String desc, String pattern, String... example) {
        Registration reg = new Registration("Effects", pattern).setName(title)
                .setDesc(desc)
                .setExample(example);
        Vixio.getInstance().syntaxElements.add(reg);
        return this;
    }

}
