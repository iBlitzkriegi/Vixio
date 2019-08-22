package me.iblitzkriegi.vixio.util.skript;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.expressions.retrieve.ExprMember;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Locale;

public class SkriptUtil {

    public static final Field VARIABLE_NAME;
    public static boolean variableNameGetterExists = Skript.methodExists(Variable.class, "getName");

    static {

        if (!variableNameGetterExists) {

            Field _VARIABLE_NAME = null;
            try {
                _VARIABLE_NAME = Variable.class.getDeclaredField("name");
                _VARIABLE_NAME.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                Skript.error("Skript's 'variable name' method could not be resolved.");
            }
            VARIABLE_NAME = _VARIABLE_NAME;

        } else {
            VARIABLE_NAME = null;
        }

    }


    // Variable name related code credit btk5h (https://github.com/btk5h)
    public static VariableString getVariableName(Variable<?> var) {
        if (variableNameGetterExists) {
            return var.getName();
        } else {
            try {
                return (VariableString) VARIABLE_NAME.get(var);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Expression<Member> combineUserAndGuild(Expression<User> user, Expression<Guild> guild) {
        ExprMember member = new ExprMember();
        member.init(new Expression[] {user, guild}, 0, ScriptLoader.hasDelayBefore, null);
        return member;
    }

    public static void setList(String name, Event e, boolean local, Object... objects) {
        if (objects == null || name == null) return;

        int separatorLength = Variable.SEPARATOR.length() + 1;
        name = name.substring(0, (name.length() - separatorLength));
        name = name.toLowerCase(Locale.ENGLISH) + Variable.SEPARATOR;
        Variables.setVariable(name + "*", null, e, local);
        for (int i = 0; i < objects.length; i++)
            Variables.setVariable(name + (i + 1), objects[i], e, local);
    }

    @SuppressWarnings("unchecked")
    public static <T> Expression<T> defaultToEventValue(Expression<T> expr, Class<T> clazz) {
        if (expr != null)
            return expr;
        Class<? extends Event>[] events = ScriptLoader.getCurrentEvents();
        for (Class<? extends Event> e : events == null ? new Class[0] : events) {
            Getter getter = EventValues.getEventValueGetter(e, clazz, 0);
            if (getter != null) {
                return new SimpleExpression<T>() {
                    @Override
                    protected T[] get(Event e) {
                        T value = (T) getter.get(e);
                        if (value == null)
                            return null;
                        T[] arr = (T[]) Array.newInstance(clazz, 1);
                        arr[0] = value;
                        return arr;
                    }

                    @Override
                    public boolean isSingle() {
                        return true;
                    }

                    @Override
                    public Class<? extends T> getReturnType() {
                        return clazz;
                    }

                    @Override
                    public boolean isDefault() {
                        return true;
                    }

                    @Override
                    public String toString(Event e, boolean debug) {
                        return "defaulted event value expression";
                    }

                    @Override
                    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
                        return true;
                    }
                };
            }
        }
        return null;
    }

}
