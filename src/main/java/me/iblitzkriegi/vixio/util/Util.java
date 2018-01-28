package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.variables.Variables;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.enums.SearchSite;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emoji;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class Util {

    private static final Field VARIABLE_NAME;
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

    private static YoutubeSearchProvider youtubeSearchProvider =
            new YoutubeSearchProvider(
                    new YoutubeAudioSourceManager(false)
            );

    public static DefaultAudioPlayerManager defaultAudioPlayerManager = new DefaultAudioPlayerManager();
    private static SoundCloudAudioSourceManager soundCloudSearchProvider = new SoundCloudAudioSourceManager(true);

    // Variable name related code credit btk5h (https://github.com/btk5h)
    public static VariableString getVariableName(Variable<?> var) {
        try {
            return (VariableString) VARIABLE_NAME.get(var);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean equalsAnyIgnoreCase(String toMatch, String... potentialMatches) {
        return Arrays.asList(potentialMatches).contains(toMatch);
    }

    public static AudioTrack[] search(SearchSite site, String[] queries) {
        List<AudioTrack> results = new ArrayList<>();
        AudioItem playlist = null;

        for (String query : queries) {

            switch (site) {

                case YOUTUBE:
                    playlist = youtubeSearchProvider.loadSearchResult(query);
                    break;

                case SOUNDCLOUD:
                    playlist = soundCloudSearchProvider.loadItem(
                            defaultAudioPlayerManager,
                            new AudioReference("scsearch:" + query, null)
                    );
                    break;

            }

            if (playlist instanceof AudioPlaylist)
                results.addAll(((AudioPlaylist) playlist).getTracks());

        }

        return results.isEmpty() ? null :
                results.toArray(new AudioTrack[results.size()]);

    }

    public static void setList(String name, Object[] objects, Event e, boolean local) {
        if (objects == null || name == null || e == null) return;

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
        return bot.getJDA() == jda;
    }

    public static Guild bindGuild(Bot bot, Guild guild) {
        if (!(guild.getJDA() == bot.getJDA())) {
            return bot.getJDA().getGuildById(guild.getId());
        } else {
            if (guild != null) {
                return guild;
            }
            return null;
        }
    }

    public static TextChannel bindChannel(Bot bot, TextChannel textChannel) {
        if (!(textChannel.getJDA() == bot.getJDA())) {
            return bot.getJDA().getTextChannelById(textChannel.getId());
        } else {
            if (textChannel != null) {
                return textChannel;
            }
            return null;
        }
    }


    public static VoiceChannel bindVoiceChannel(Bot bot, VoiceChannel voiceChannel) {
        if (!(voiceChannel.getJDA() == bot.getJDA())) {
            return bot.getJDA().getVoiceChannelById(voiceChannel.getId());
        } else {
            if (voiceChannel != null) {
                return voiceChannel;
            }
            return null;
        }
    }

    public static Channel bindChannel(Bot bot, Channel channel) {
        if (!(channel.getJDA() == bot.getJDA())) {
            TextChannel textChannel = bot.getJDA().getTextChannelById(channel.getId());
            VoiceChannel voiceChannel = bot.getJDA().getVoiceChannelById(channel.getId());

            return voiceChannel == null ? textChannel : voiceChannel;
        } else {
            if (channel != null) {
                return channel;
            }
            return null;
        }
    }

    public static Emoji unicodeFrom(String emote, Guild guild) {
        try {
            if (EmojiManager.isEmoji(emote)) {
                return new Emoji(emote);
            }
            Collection<Emote> emotes = guild.getEmotesByName(emote, false);
            return emotes.isEmpty() ? new Emoji(EmojiParser.parseToUnicode(":" + emote + ":")) : new Emoji(emotes.iterator().next());
        }catch (UnsupportedOperationException | NoSuchElementException x) {
            return null;
        }
    }
    public static Emoji unicodeFrom(String emote) {
        if (EmojiManager.isEmoji(emote)) {
            return new Emoji(emote);
        } else {
            return new Emoji(EmojiParser.parseToUnicode(emote));
        }
    }

}
