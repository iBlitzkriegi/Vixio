package me.iblitzkriegi.vixio.util;

public interface Converter<F, T> {

    T[] convert(F from);

}
