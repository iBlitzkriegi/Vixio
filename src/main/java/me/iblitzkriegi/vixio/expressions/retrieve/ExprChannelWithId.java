package me.iblitzkriegi.vixio.expressions.retrieve;

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

import java.util.Set;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprChannelWithId extends SimpleExpression<Channel> {
    static {
        Vixio.getInstance().registerExpression(ExprChannelWithId.class, Channel.class, ExpressionType.SIMPLE,
                "[(voice|text)][(-| )]channel with id %string% [in %guild%]")
                .setName("Channel with ID")
                .setDesc("Get a Text or Voice channel via it's ID.")
                .setExample("");
    }
    private Expression<String> id;
    private Expression<Guild> guild;
    @Override
    protected Channel[] get(Event event) {
        return new Channel[]{getChannel(event)};
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
    public String toString(Event e, boolean b) {
        return guild != null ? "channel with id " + id.toString(e, b) + " in guild " + guild.toString(e, b) : "channel with id " + id.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        guild = (Expression<Guild>) expressions[1];
        return true;
    }
    private Channel getChannel(Event e) {
        String id = this.id.getSingle(e);
        if (id == null || id.isEmpty()) {
            return null;
        }
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
            if (jdaInstances == null) {
                Vixio.getErrorHandler().warn("Vixio tried to retrieve a channel but no bots were logged in! At least one bot is needed.");
                return null;
            }
            for (JDA jda : jdaInstances) {
                TextChannel textChannel = jda.getTextChannelById(id);
                VoiceChannel voiceChannel = jda.getVoiceChannelById(id);
                if (textChannel != null) {
                    return textChannel;
                } else if(voiceChannel != null){
                    return voiceChannel;
                }
            }
            return null;
        }
        TextChannel textChannel = guild.getTextChannelById(id);
        VoiceChannel voiceChannel = guild.getVoiceChannelById(id);
        if(textChannel != null){
            return textChannel;
        }
        if(voiceChannel != null){
            return voiceChannel;
        }
        Vixio.getErrorHandler().warn("Vixio attempted to find a channel with the id " + id + " in " + guild.getName() + " but was unable to find anything.");
        return null;
    }
}
