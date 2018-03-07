package me.iblitzkriegi.vixio.expressions.retrieve.channel;

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

public class ExprChannelWithId extends SimpleExpression<Channel> {
    static {
        Vixio.getInstance().registerExpression(ExprChannelWithId.class, Channel.class, ExpressionType.SIMPLE,
                "channel with id %string% [in %-guild%]")
                .setName("Channel with ID")
                .setDesc("Grabs a channel by its ID")
                .setExample("broadcast \"%name of channel with id \"\"6515615645451561\"\"%");
    }

    private Expression<String> id;
    private Expression<Guild> guild;

    @Override
    protected Channel[] get(Event e) {
        String id = this.id.getSingle(e);
        if (id == null || id.isEmpty()) {
            return null;
        }

        if (guild == null) {
            Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
            if (jdaInstances.isEmpty()) {
                return null;
            }

            List<Channel> channels = new ArrayList<>();
            for (JDA jda : jdaInstances) {
                TextChannel textChannel = jda.getTextChannelById(id);
                VoiceChannel voiceChannel = jda.getVoiceChannelById(id);
                if (textChannel != null) {
                    channels.add(textChannel);
                } else if (voiceChannel != null) {
                    channels.add(voiceChannel);
                }

            }

            return new Channel[]{channels.get(0)};
        }

        Guild guild = this.guild.getSingle(e);
        TextChannel textChannel = guild.getTextChannelById(id);
        VoiceChannel voiceChannel = guild.getVoiceChannelById(id);
        List<Channel> channels = new ArrayList<>();
        if (textChannel != null) {
            channels.add(textChannel);
        } else if (voiceChannel != null) {
            channels.add(voiceChannel);
        }

        return new Channel[]{channels.get(0)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Channel> getReturnType() {
        return Channel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "channel with id " + id.toString(e, debug) + (guild == null ? "" : " in " + guild.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
