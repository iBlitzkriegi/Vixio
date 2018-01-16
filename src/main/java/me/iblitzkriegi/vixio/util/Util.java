package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.variables.Variables;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

    private static final Field VARIABLE_NAME;
    private static final Method SOUNDCLOUD_SEARCH;
    private static Integer defaultSearchResults = null;

    private static YoutubeSearchProvider youtubeSearchProvider =
            new YoutubeSearchProvider(
                    new YoutubeAudioSourceManager(false)
            );

    private static SoundCloudAudioSourceManager soundCloudAudioSourceManager =
            new SoundCloudAudioSourceManager(
                    false
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

        Method _SOUNDCLOUD_SEARCH = null;
        try {
            _SOUNDCLOUD_SEARCH = SoundCloudAudioSourceManager.class.getDeclaredMethod("loadSearchResult", String.class, int.class, int.class);
            _SOUNDCLOUD_SEARCH.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Skript.error("Vixio couldn't find Lavaplayer's SoundCloud search method.");
        }
        SOUNDCLOUD_SEARCH = _SOUNDCLOUD_SEARCH;

        Field _DEFAULT_SEARCH_RESULTS = null;
        try {
            _DEFAULT_SEARCH_RESULTS = SoundCloudAudioSourceManager.class.getDeclaredField("DEFAULT_SEARCH_RESULTS");
            _DEFAULT_SEARCH_RESULTS.setAccessible(true);
            defaultSearchResults = (Integer) _DEFAULT_SEARCH_RESULTS.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            Skript.error("Vixio couldn't find Lavaplayer's default search result field");
        }

    }

    // Variable name related code credit btk5h (https://github.com/btk5h)
    public static VariableString getVariableName(Variable<?> var) {
        try {
            return (VariableString) VARIABLE_NAME.get(var);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
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
                break;

            case SOUNDCLOUD:
                for (String query : queries) {
                    AudioItem playlist = null;
                    try {
                        playlist = (AudioItem) SOUNDCLOUD_SEARCH.invoke(soundCloudAudioSourceManager, query, 0, defaultSearchResults);
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                        Skript.error("Vixio encountered an error while trying to search soundcloud for " + query);
                        e.printStackTrace();
                    }
                    if (playlist instanceof AudioPlaylist)
                        results.addAll(((AudioPlaylist) playlist).getTracks());
                }
                break;

        }

        return results.isEmpty() ? null :
                results.toArray(new AudioTrack[results.size()]);

    }

    public static void setList(String name, Object[] objects, Event e, boolean local) {
        if (objects == null) return;

        for (int i = 0; i < objects.length; i++)
            Variables.setVariable(name.toLowerCase(Locale.ENGLISH) + Variable.SEPARATOR + (i + 1), objects[i], e, local);
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
