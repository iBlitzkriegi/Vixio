package me.iblitzkriegi.vixio.expressions.objects;

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
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprChannelWithId extends SimpleExpression<Channel> {
    static {
        Vixio.getInstance().registerExpression(ExprChannelWithId.class, Channel.class, ExpressionType.SIMPLE, "[(voice|text)][(-| )]channel with id %string% [in guild %-string%]");
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
    private Channel getChannel(Event e){
        if(id != null) {
            if(guild == null) {
                if (Vixio.getInstance().botHashMap.keySet() != null) {
                    for (JDA jda : Vixio.getInstance().botHashMap.keySet()) {
                        if (jda.getTextChannelById(id.getSingle(e)) == null) {
                            if(jda.getVoiceChannelById(id.getSingle(e))==null){
                                Skript.error("Could not find TextChannel or VoiceChannel with the provided ID, check your ID and try again.");
                                return null;
                            }else{
                                return jda.getVoiceChannelById(id.getSingle(e));
                            }
                        }else{
                            return jda.getTextChannelById(id.getSingle(e));
                        }
                    }
                } else {
                    Skript.error("You must login to a bot via the connect effect before you may attempt to use this expression.");
                    return null;
                }
            }else{
                Guild g = guild.getSingle(e);
                if(g.getTextChannelById(id.getSingle(e))==null){
                    if(g.getVoiceChannelById(id.getSingle(e))==null){
                        Skript.error("Could not find TextChannel via the provided ID");
                        return null;
                    }else{
                        return g.getVoiceChannelById(id.getSingle(e));
                    }
                }else{
                    return g.getTextChannelById(id.getSingle(e));
                }
            }
        }else{
            Skript.error("You must provide a ID to use this expression! May not leave blank.");
            return null;
        }
        Skript.error("Could not find VoiceChannel or a TextChannel via the provided id.");
        return null;

    }
}
