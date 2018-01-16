package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.variables.Variables;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.enums.SearchSite;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

    private static final Field VARIABLE_NAME;
    public static YoutubeSearchProvider youtubeSearchProvider =
            new YoutubeSearchProvider(
                    new YoutubeAudioSourceManager(false)
            );

    static {
        Field _VARIABLE_NAME = null;
        try {
            _VARIABLE_NAME = Variable.class.getDeclaredField("name");
            _VARIABLE_NAME.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Skript.error("Skript's 'variable name' method could not be resolved.");
        }
        VARIABLE_NAME = _VARIABLE_NAME;
    }

    public static AudioTrack[] search(SearchSite site, String[] queries) {
        List<AudioTrack> results = new ArrayList<>();
        switch (site) {
            case YOUTUBE:
                for (String query : queries) {
                    AudioItem playlist = youtubeSearchProvider.loadSearchResult(query);
                    if (playlist instanceof AudioPlaylist)
                        results.addAll(((AudioPlaylist) playlist).getTracks());
                }
        }

        return results.isEmpty() ? null :
                results.toArray(new AudioTrack[results.size()]);
    }

    public static void setList(String name, Object[] objects, Event e, boolean local) {
        for (int i = 0; i < objects.length; i++)
            Variables.setVariable(name.toLowerCase(Locale.ENGLISH) + Variable.SEPARATOR + (i + 1), objects[i], e, local);
    }

    public static VariableString getVariableName(Variable<?> var) {
        try {
            return (VariableString) VARIABLE_NAME.get(var);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public static Guild bindGuild(Bot bot, Guild guild) {
        return bot.getJDA().getGuildById(guild.getId());
    }

}
