package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExprChannelNamed extends SimpleExpression<Channel> {

    static {
        Vixio.getInstance().registerExpression(ExprChannelNamed.class, Channel.class, ExpressionType.SIMPLE,
                "[(1¦voice|2¦text)][(-| )]channel[<s>] (named|with name) %string% [in %-guild%]")
                .setName("Channel Named")
                .setDesc("Get a channel via it's name, you can include the type of channel and/or the guild to speed the retrieval process up. The searching is case sensitive.")
                .setUserFacing("[(voice|text)][(-| )]channel[s] named %string% [in %-guild%]")
                .setExample(
                        "discord command move <text>:",
                        "\ttrigger:",
                        "\t\tset {_} to voice channel named arg-1",
                        "\t\tif voice channel of event-member is not set:",
                        "\t\t\treply with \"Not in vc to move to\"",
                        "\t\t\tstop",
                        "\t\tmove event-member to {_}",
                        "\t\treply with \"Done! %event-member% was moved to %{_}%\""
                );
    }

    private boolean singular;
    private Expression<Guild> guild;
    private Expression<String> name;
    private int mark;

    @Override
    protected Channel[] get(Event e) {
        String name = this.name.getSingle(e);
        Guild guild = this.guild == null ? null : this.guild.getSingle(e);
        if (name == null) {
            return null;
        }
        List<VoiceChannel> voiceChannels;
        List<TextChannel> textChannels;

        if (guild != null) {
            if (mark == 0) {
                voiceChannels = guild.getVoiceChannelsByName(name, false);
                textChannels = guild.getTextChannelsByName(name, false);
                if (voiceChannels.isEmpty() && textChannels.isEmpty()) {
                    return null;
                }
                if (singular) {
                    Channel channel = textChannels.isEmpty() ? voiceChannels.get(0) : textChannels.get(0);
                    return new Channel[]{channel};
                }
                List<Channel> channels = new ArrayList<>();
                int size = 0;
                if (!textChannels.isEmpty()) {
                    channels.addAll(textChannels);
                    size = size + textChannels.size();
                }
                if (!voiceChannels.isEmpty()) {
                    channels.addAll(voiceChannels);
                    size = size + voiceChannels.size();
                }
                return channels.toArray(new Channel[size]);
            } else if (mark == 1) {
                voiceChannels = guild.getVoiceChannelsByName(name, false);
                if (voiceChannels.isEmpty()) {
                    return null;
                }
                return singular ? new VoiceChannel[]{voiceChannels.get(0)} : voiceChannels.toArray(new VoiceChannel[voiceChannels.size()]);
            } else if (mark == 2) {
                textChannels = guild.getTextChannelsByName(name, false);
                if (textChannels.isEmpty()) {
                    return null;
                }
                return singular ? new TextChannel[]{textChannels.get(0)} : textChannels.toArray(new TextChannel[textChannels.size()]);
            }
        }
        Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
        for (JDA jda : jdaInstances) {
            if (mark == 0) {
                voiceChannels = jda.getVoiceChannelByName(name, false);
                textChannels = jda.getTextChannelsByName(name, false);
                if (!(voiceChannels.isEmpty()) || (!(textChannels.isEmpty()))) {
                    if (singular) {
                        return voiceChannels.isEmpty() ? new TextChannel[]{textChannels.get(0)} : new VoiceChannel[]{voiceChannels.get(0)};
                    }
                    List<Channel> channels = new ArrayList<>();
                    int size = 0;
                    if (!textChannels.isEmpty()) {
                        channels.addAll(textChannels);
                        size = size + textChannels.size();
                    }
                    if (!voiceChannels.isEmpty()) {
                        channels.addAll(voiceChannels);
                        size = size + voiceChannels.size();
                    }
                    return channels.toArray(new Channel[size]);
                }
            } else if (mark == 1) {
                voiceChannels = jda.getVoiceChannelByName(name, false);
                if (!voiceChannels.isEmpty()) {
                    return singular ? new VoiceChannel[]{voiceChannels.get(0)} : voiceChannels.toArray(new VoiceChannel[voiceChannels.size()]);
                }
            } else if (mark == 2) {
                textChannels = jda.getTextChannelsByName(name, false);
                if (!textChannels.isEmpty()) {
                    return singular ? new TextChannel[]{textChannels.get(0)} : textChannels.toArray(new TextChannel[textChannels.size()]);
                }
            }

        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return singular;
    }

    @Override
    public Class<? extends Channel> getReturnType() {
        return Channel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "channel named " + name.toString(e, debug) + (guild == null ? "" : " in " + guild.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        singular = parseResult.regexes.size() == 0;
        mark = parseResult.mark;
        name = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
