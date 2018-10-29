package me.iblitzkriegi.vixio.util.skript;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.variables.Variables;
import me.iblitzkriegi.vixio.expressions.retrieve.ExprMember;
import me.iblitzkriegi.vixio.util.ReflectionUtils;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

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


    public static String getClassInfoName(Class<?> clazz) {
        ClassInfo<?> classInfo = Classes.getExactClassInfo(clazz);
        if (classInfo == null) {
            classInfo = Classes.getExactClassInfo(Object.class);
        }
        return classInfo.getCodeName();
    }

    public static List<String> getEventValues(Class<? extends Event>... classes) {

        Method m = ReflectionUtils.getMethod(EventValues.class, "getEventValuesList", int.class);
        List<?> values = ReflectionUtils.invokeMethod(m, null, 0);
        List<String> eventValues = new ArrayList<>();
        if (values != null)
            for (Class<?> c : classes) {
                for (Object eventValue : values) {
                    Class<?> event = ReflectionUtils.getField(eventValue.getClass(), eventValue, "event");
                    if (event != null && (c.isAssignableFrom(event) || event.isAssignableFrom(c))) {
                        Class<?> ret = ReflectionUtils.getField(eventValue.getClass(), eventValue, "c");
                        eventValues.add("event-" + getClassInfoName(ret));
                    }
                }
            }
        return eventValues;
    }

}
