package me.iblitzkriegi.vixio.util;

import ch.njol.skript.util.Date;
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
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.Bukkit;

import java.awt.Color;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;

public class Util {

    public static final int DEFAULT_BITRATE = 64000;

    private static HashMap<String, Color> colors = new HashMap<>();

    private static DefaultAudioPlayerManager defaultAudioPlayerManager = new DefaultAudioPlayerManager();
    private static YoutubeSearchProvider youtubeSearchProvider =
            new YoutubeSearchProvider(
                    new YoutubeAudioSourceManager(false)
            );
    private static SoundCloudAudioSourceManager soundCloudSearchProvider = new SoundCloudAudioSourceManager(true);

    static {
        try {
            for (Field color : Color.class.getDeclaredFields()) {
                color.setAccessible(true);
                if (color.getType() == Color.class) {
                    colors.put(color.getName().toLowerCase(Locale.ENGLISH).replace("_", " "), (Color) color.get(null));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

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
        return str == null ? null : colors.get(str.toLowerCase(Locale.ENGLISH));

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

    public static Bot botFromID(String ID) {
        return Vixio.getInstance().botHashMap.values().stream()
                .filter(b -> ID.equals(b.getSelfUser().getId()))
                .findFirst()
                .orElse(null);
    }

    public static Message messageFrom(Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Message) {
            return (Message) input;
        } else if (input instanceof UpdatingMessage) {
            return ((UpdatingMessage) input).getMessage();
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
        if (guild == null || bot == null) {
            return null;
        }
        if (!(guild.getJDA() == bot.getJDA())) {
            return bot.getJDA().getGuildById(guild.getId());
        } else {
            return guild;
        }
    }

    public static TextChannel bindChannel(Bot bot, TextChannel textChannel) {
        if (bot == null || textChannel == null) {
            return null;
        }
        if (!(textChannel.getJDA() == bot.getJDA())) {
            return bot.getJDA().getTextChannelById(textChannel.getId());
        } else {
            return textChannel;
        }
    }

    public static MessageChannel bindMessageChannel(Bot bot, MessageChannel channel) {
        if (bot == null || channel == null) {
            return null;
        }

        if (!(channel.getJDA() == bot.getJDA())) {
            PrivateChannel privateChannel = bot.getJDA().getPrivateChannelById(channel.getId());
            TextChannel textChannel = bot.getJDA().getTextChannelById(channel.getId());
            if (privateChannel != null) {
                return privateChannel;
            } else if (textChannel != null) {
                return textChannel;
            }

            return null;
        } else {
            return channel;
        }
    }

    public static Message bindMessage(Bot bot, Message message) {
        if (!(bot.getJDA() == message.getJDA())) {
            try {
                return bot.getJDA().getTextChannelById(message.getChannel().getId()).getMessageById(message.getId()).complete(true);
            } catch (RateLimitedException e) {

            }
        } else {
            return message;
        }
        return null;
    }

    public static MessageChannel bindChannel(Bot bot, MessageChannel messageChannel) {
        if (messageChannel.getJDA() == bot.getJDA()) {
            return messageChannel;
        }

        if (messageChannel.getType() == ChannelType.PRIVATE || messageChannel.getType() == ChannelType.GROUP) {
            return bot.getJDA().getPrivateChannelById(messageChannel.getId());
        } else {
            return bot.getJDA().getTextChannelById(messageChannel.getId());
        }
    }

    public static VoiceChannel bindVoiceChannel(Bot bot, VoiceChannel voiceChannel) {
        if (!(voiceChannel.getJDA() == bot.getJDA())) {
            return bot.getJDA().getVoiceChannelById(voiceChannel.getId());
        } else {
            return voiceChannel;
        }
    }

    public static User bindUser(Bot bot, User user) {
        if (user == null || bot == null) {
            return null;
        }

        if (user.getJDA() == bot.getJDA()) {
            return user;
        } else {
            return bot.getJDA().getUserById(user.getId());
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

    public static Emote unicodeFrom(String input, Guild guild) {
        String id = input.replaceAll("[^0-9]", "");
        if (id.isEmpty()) {
            try {
                if (guild == null) {
                    Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
                    for (JDA jda : jdaInstances) {
                        Collection<net.dv8tion.jda.core.entities.Emote> emoteCollection = jda.getEmotesByName(input, false);
                        if (!emoteCollection.isEmpty()) {
                            return new Emote(emoteCollection.iterator().next());
                        }
                    }
                    return unicodeFrom(input);
                }
                Collection<net.dv8tion.jda.core.entities.Emote> emotes = guild.getEmotesByName(input, false);
                if (emotes.isEmpty()) {
                    return unicodeFrom(input);
                }

                return new Emote(emotes.iterator().next());
            } catch (UnsupportedOperationException | NoSuchElementException x) {
                return null;
            }
        } else {
            if (guild == null) {
                Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
                for (JDA jda : jdaInstances) {
                    net.dv8tion.jda.core.entities.Emote emote = jda.getEmoteById(id);
                    if (emote != null) {
                        return new Emote(emote);
                    }
                }
                return unicodeFrom(input);
            }
            try {
                net.dv8tion.jda.core.entities.Emote emote = guild.getEmoteById(id);
                if (emote == null) {
                    net.dv8tion.jda.core.entities.Emote emote1 = guild.getJDA().getEmoteById(id);
                    if (!(emote1 == null)) {
                        return new Emote(emote1);
                    }
                    return null;
                }

                return new Emote(emote);
            } catch (UnsupportedOperationException | NoSuchElementException x) {
                return null;
            }
        }
    }

    public static Emote unicodeFrom(String input) {
        if (EmojiManager.isEmoji(input)) {
            return new Emote(input);
        } else {
            String emote = input.contains(":") ? input : ":" + input + ":";
            return new Emote(EmojiParser.parseToUnicode(emote));
        }
    }

    public static Date getDate(OffsetDateTime date) {
        return new Date(date.toInstant().getEpochSecond() * 1000);
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

    public static Member getMemberFromUser(User input) {
        Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
        for (JDA jda : jdaInstances) {
            if (jda.getSelfUser().getId().equalsIgnoreCase(input.getId())) {
                return jda.getGuilds().isEmpty() ? null : jda.getGuilds().get(0).getSelfMember();
            }
            User user = jda.getUserById(input.getId());
            if (user != null) {
                List<Guild> guildList = jda.getMutualGuilds(user);
                if (guildList != null) {
                    return guildList.iterator().next().getMember(user);
                }
            }
        }
        return null;
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(Vixio.getInstance(), runnable);
    }

    public static void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(Vixio.getInstance(), runnable);
    }

}

