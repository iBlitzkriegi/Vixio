package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.awt.Color;
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

    public static Bot botFrom(Object input){
        if (input == null) {
            return null;
        } else if(input instanceof Bot) {
            return (Bot) input;
        } else if(input instanceof String) {
            String string = (String) input;
            Bot bot = Vixio.getInstance().botNameHashMap.get(string);
            if (bot != null) {
                return bot;
            }
        }
        return null;
    }

    public static Message messageFrom(Object input) {
        if (input instanceof Message) {
            return (Message) input;
        } else if (input instanceof String) {
            try {
                return new MessageBuilder()
                        .setContent((String) input)
                        .build();
            } catch (IllegalStateException | IllegalArgumentException x) {
                return null;
            }
        }
        return null;
    }

    public static boolean botIsConnected(Bot bot, JDA jda){
        return bot.getJDA().getSelfUser().getId().equalsIgnoreCase(jda.getSelfUser().getId());
    }

}
