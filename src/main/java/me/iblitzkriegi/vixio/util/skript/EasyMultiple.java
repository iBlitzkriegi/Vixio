package me.iblitzkriegi.vixio.util.skript;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

// idk a better name for this, meant for use w/ expressions, see
public interface EasyMultiple<F, T> {

    default T[] convert(Class<? extends T> returnType, F[] from, Converter<F, T> converter) {
        if (from == null || from.length == 0) {
            return null;
        }
        ArrayList<T> results = new ArrayList<>();
        Arrays.stream(from)
                .filter(Objects::nonNull)
                .forEach(e -> Collections.addAll(results, converter.convert(e)));
        return results.isEmpty() ? null : results.toArray((T[]) Array.newInstance(returnType, results.size()));
    }

    /**
     * Lets you change multiple things easily
     *
     * @param changed The F's of objects being changed
     * @param changer - a changer representing what you want to do to each F
     */
    default void change(F[] changed, EasyChanger<F> changer) {
        if (changed == null || changed.length == 0) {
            return;
        }
        Arrays.stream(changed)
                .filter(Objects::nonNull)
                .forEach(changer::change);
    }

}
