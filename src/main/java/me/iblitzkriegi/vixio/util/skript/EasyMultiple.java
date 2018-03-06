package me.iblitzkriegi.vixio.util.skript;

import ch.njol.util.Checker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

// idk a better name for this, meant for use w/ expressions
public interface EasyMultiple<F, T> {

    /**
     * Provides an easy way of converting single or multiple inputs without the hassle of
     * null checks and Lists
     *
     * @param returnType The type the expression returns (used to initialize the array)
     * @param from       The objects to be converted
     * @param converter  The converter to apply to {@param from}
     * @return An array of all the converted objects, possibly null
     */
    default T[] convert(Class<? extends T> returnType, F[] from, Converter<F, T> converter) {
        if (from == null || from.length == 0) {
            return null;
        }
        ArrayList<T> results = new ArrayList<>();
        Arrays.stream(from)
                .filter(Objects::nonNull)
                .forEach(e -> {
                    T[] arr = converter.convert(e);
                    if (arr != null) {
                        Collections.addAll(results, arr);
                    }
                });
        return results.isEmpty() ? null : results.toArray((T[]) Array.newInstance(returnType, results.size()));
    }

    /**
     * Lets you change multiple things easily
     *
     * @param changed The F's of objects being changed
     * @param changer - a changer representing what you want to do to each F object
     */
    default void change(F[] changed, EasyChanger<F> changer) {
        if (changed == null || changed.length == 0) {
            return;
        }
        Arrays.stream(changed)
                .filter(Objects::nonNull)
                .forEach(changer::change);
    }

    /**
     * Lets you check multiple things easily
     *
     * @param checked The F's of objects being checked
     * @param checker - a changer representing what you want to do to each F object
     * @param negated - whether or not to negate the result
     */
    default boolean check(F[] checked, Checker<F> checker, boolean negated) {
        if (checked == null || checked.length == 0) {
            return false;
        }

        return negated ? Arrays.stream(checked)
                .filter(Objects::nonNull)
                .noneMatch(checker::check) : Arrays.stream(checked)
                .filter(Objects::nonNull)
                .allMatch(checker::check);
    }


}
