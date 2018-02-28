package me.iblitzkriegi.vixio.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// idk a better name for this, meant for use w/ expression
public interface Convertable<F, T> {

    default T[] convert(Class<? extends T> returnType, F[] from, Converter<F, T> converter) {
        ArrayList<T> results = new ArrayList<>();
        Arrays.stream(from)
                .forEach(e -> Collections.addAll(results, converter.convert(e)));
        return results.isEmpty() ? null : results.toArray((T[]) Array.newInstance(returnType, results.size()));
    }

}
