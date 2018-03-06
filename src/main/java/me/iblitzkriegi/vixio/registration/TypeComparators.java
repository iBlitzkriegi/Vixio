package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.classes.Comparator;
import ch.njol.skript.registrations.Comparators;
import me.iblitzkriegi.vixio.util.wrapper.Bot;

public class TypeComparators {

    public static void register() {
        Comparators.registerComparator(Bot.class, String.class, new Comparator<Bot, String>() {

            @Override
            public Relation compare(Bot bot, String str) {
                return Relation.get(bot.getName().equals(str));
            }

            @Override
            public boolean supportsOrdering() {
                return false;
            }

        });
    }

}
