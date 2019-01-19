package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SyntaxElement;
import ch.njol.skript.lang.SyntaxElementInfo;
import me.iblitzkriegi.vixio.changers.EffChange;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ParseOrderWorkaround {
    public static void reorderSyntax() {
        ensureFirst(Skript.getStatements());
        ensureFirst(Skript.getConditions());
        ensureFirst(Skript.getEffects());
    }

    private static <E extends SyntaxElement> void ensureFirst(Collection<SyntaxElementInfo<? extends E>> elements) {
        Optional<SyntaxElementInfo<? extends E>> optionalElementInfo = elements.stream()
                .filter(info -> info.c.getName().equals(EffChange.class.getName()))
                .findFirst();
        optionalElementInfo.ifPresent(e -> {
            elements.remove(e);
            ((List) elements).add(0, e);
        });


    }
}
