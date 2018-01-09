package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.Skript;
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

import javax.xml.soap.Text;
import java.util.Set;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprChannelWithId extends SimpleExpression<Channel> {
    static {
        Vixio.getInstance().registerExpression(ExprChannelWithId.class, Channel.class, ExpressionType.SIMPLE,
                "[(voice|text)][(-| )]channel with id %string% [in %-guild%]")
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
        if (id == null) {
            Skript.error("You must include a ID to use to find a channel!");
            return null;
        }
        String id = this.id.getSingle(e);
        if (guild == null) {
            Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
            if (jdaInstances == null) {
                Skript.error("You must first login to a bot to use this syntax.");
                return null;
            }
            for (JDA jda : jdaInstances) {
                TextChannel textChannel = jda.getTextChannelById(id);
                VoiceChannel voiceChannel = jda.getVoiceChannelById(id);
                if(textChannel != null){
                    return textChannel;
                }else if(voiceChannel != null){
                    return voiceChannel;
                }
            }
            Skript.error("Could not find channel via that ID");
            return null;
        }
        Guild guild = this.guild.getSingle(e);
        for(TextChannel channel : guild.getTextChannels()){
            if(channel.getId().equalsIgnoreCase(id)){
                return channel;
            }
        }
        for (VoiceChannel channel : guild.getVoiceChannels()){
            if(channel.getId().equalsIgnoreCase(id)){
                return channel;
            }
        }
        Skript.error("Could not find channel via that ID");
        return null;
    }
}
