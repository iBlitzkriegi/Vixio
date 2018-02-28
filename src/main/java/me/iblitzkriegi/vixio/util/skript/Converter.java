package me.iblitzkriegi.vixio.util.skript;

public interface Converter<F, T> {

    T[] convert(F from);

}
