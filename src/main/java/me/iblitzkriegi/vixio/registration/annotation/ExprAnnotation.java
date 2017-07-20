package me.iblitzkriegi.vixio.registration.annotation;

import ch.njol.skript.lang.ExpressionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Blitz on 10/30/2016.
 */
public class ExprAnnotation {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Expression {
        String name();
        String title();
        String desc();
        String syntax();
        Class returntype();
        ExpressionType type();
        String example();
    }

}
