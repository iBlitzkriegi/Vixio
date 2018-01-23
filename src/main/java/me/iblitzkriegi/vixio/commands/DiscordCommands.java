package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.Skript;
import ch.njol.skript.command.Argument;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.variables.Variables;
import me.iblitzkriegi.vixio.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordCommands {

    public static Map<String, DiscordCommand> commandMap = new HashMap<>();
    private static final Method PARSE_I;

    static {

        Method _PARSE_I = null;
        try {
            _PARSE_I = SkriptParser.class.getDeclaredMethod("parse_i", String.class, int.class, int.class);
            _PARSE_I.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Skript.error("Skript's 'parse_i' method could not be resolved.");
        }
        PARSE_I = _PARSE_I;

    }

    public static boolean parseArguments(String args, DiscordCommand command, DiscordCommandEvent event) {
        SkriptParser parser = new SkriptParser(args, SkriptParser.PARSE_LITERALS, ParseContext.COMMAND);
        SkriptParser.ParseResult res = null;
        try {
            res = (SkriptParser.ParseResult) PARSE_I.invoke(parser, command.getPattern(), 0, 0);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (res == null)
            return false;

        List<Argument<?>> as = command.getArguments();
        assert as.size() == res.exprs.length;
        for (int i = 0; i < res.exprs.length; i++) {
            Argument<?> arg = as.get(i);
            if (res.exprs[i] == null)
                as.get(i).setToDefault(event);
            else
                as.get(i).set(event, res.exprs[i].getArray(event));
        }
        return true;
    }

}
