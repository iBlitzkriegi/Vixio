package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;

import java.awt.*;
import java.util.Locale;

public class Util {

    public static Color getColorFromString(String str) {

        if (str == null) return null;

        Color color = null;

        try {
            color = (Color) Color.class.getField(str.toUpperCase(Locale.ENGLISH).replace(" ", "_")).get(null);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e1) {
            Skript.exception(e1);
        }

        return color;

    }

}
