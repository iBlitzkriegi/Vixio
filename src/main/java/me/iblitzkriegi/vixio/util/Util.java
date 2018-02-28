package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
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
import me.iblitzkriegi.vixio.util.enums.SearchableSite;
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

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Random;

public class Util {

    public static DefaultAudioPlayerManager defaultAudioPlayerManager = new DefaultAudioPlayerManager();
    private static YoutubeSearchProvider youtubeSearchProvider =
            new YoutubeSearchProvider(
                    new YoutubeAudioSourceManager(false)
            );
    private static SoundCloudAudioSourceManager soundCloudSearchProvider = new SoundCloudAudioSourceManager(true);

    public static boolean equalsAnyIgnoreCase(String toMatch, String... potentialMatches) {
        return Arrays.asList(potentialMatches).contains(toMatch);
    }

    public static AudioTrack[] search(SearchableSite site, String[] queries) {
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

            if (playlist instanceof AudioPlaylist) {
                results.addAll(((AudioPlaylist) playlist).getTracks());
            }

        }

        return results.isEmpty() ? null :
                results.toArray(new AudioTrack[results.size()]);

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

    public static Bot botFrom(Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Bot) {
            return (Bot) input;
        } else if (input instanceof String) {
            return Vixio.getInstance().botNameHashMap.get(input);
        } else if (input instanceof JDA) {
            return Vixio.getInstance().botHashMap.get(input);
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

    public static boolean botIsConnected(Bot bot, JDA jda) {
        return bot.getJDA() == jda;
    }

    public static Guild bindGuild(Bot bot, Guild guild) {
        if (!(guild.getJDA() == bot.getJDA())) {
            return bot.getJDA().getGuildById(guild.getId());
        } else {
            return guild;
        }
    }

    public static TextChannel bindChannel(Bot bot, TextChannel textChannel) {
        if (!(textChannel.getJDA() == bot.getJDA())) {
            return bot.getJDA().getTextChannelById(textChannel.getId());
        } else {
            return textChannel;
        }
    }


    public static VoiceChannel bindVoiceChannel(Bot bot, VoiceChannel voiceChannel) {
        if (!(voiceChannel.getJDA() == bot.getJDA())) {
            return bot.getJDA().getVoiceChannelById(voiceChannel.getId());
        } else {
            return voiceChannel;
        }
    }

    public static Channel bindChannel(Bot bot, Channel channel) {
        if (!(channel.getJDA() == bot.getJDA())) {
            TextChannel textChannel = bot.getJDA().getTextChannelById(channel.getId());
            VoiceChannel voiceChannel = bot.getJDA().getVoiceChannelById(channel.getId());

            return voiceChannel == null ? textChannel : voiceChannel;
        } else {
            return channel;
        }
    }

    public static Emoji unicodeFrom(String emote, Guild guild) {
        try {
            if (EmojiManager.isEmoji(emote)) {
                return new Emoji(emote);
            }
            Collection<Emote> emotes = guild.getEmotesByName(emote, false);
            return emotes.isEmpty() ? new Emoji(EmojiParser.parseToUnicode(":" + emote + ":")) : new Emoji(emotes.iterator().next());
        } catch (UnsupportedOperationException | NoSuchElementException x) {
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

    public static Bot randomBot() {
        Collection<Bot> bots = Vixio.getInstance().botHashMap.values();
        return bots.isEmpty() ? null : bots.toArray(new Bot[bots.size()])[new Random().nextInt(bots.size())];
    }

    public static <T> T[] convertedArray(Class<T> convertTo, Object... objects) {
        if (objects != null) {
            T[] newArray = (T[]) Array.newInstance(convertTo, objects.length);
            for (int i = 0; i < objects.length; i++) {
                if (!convertTo.isInstance(objects[i])) {
                    throw new RuntimeException("Tried to convert an array, but encountered an object that isn't an instance of the class to convert to");
                }
                newArray[i] = (T) objects[i];
            }
            return newArray;
        }
        return null;
    }
}

