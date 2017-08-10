package me.iblitzkriegi.vixio.expressions.objects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprTextchannelWithId extends SimpleExpression<TextChannel> {
    static {
        Vixio.registerExpression(ExprTextchannelWithId.class, TextChannel.class, ExpressionType.SIMPLE, "text[-]channel with id %string% [in guild %guild%]");
    }
    private Expression<String> id;
    private Expression<Guild> guild;
    @Override
    protected TextChannel[] get(Event event) {
        return new TextChannel[]{getTextChannel(event)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends TextChannel> getReturnType() {
        return TextChannel.class;
    }

    @Override
    public String toString(Event e, boolean b) {
        return guild.getSingle(e) != null ? "text-channel with id " + id.getSingle(e) + " in guild " + guild.getSingle(e) : "text-channel with id " + id.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        guild = (Expression<Guild>) expressions[1];
        return true;
    }
    private TextChannel getTextChannel(Event e){
        if(id.getSingle(e)!=null) {
            if(guild.getSingle(e)==null) {
                if (Vixio.jdaInstances != null) {
                    for (JDA jda : Vixio.jdaInstances) {
                        if (jda.getTextChannelById(id.getSingle(e)) != null) {
                            return jda.getTextChannelById(id.getSingle(e));
                        }
                    }
                    Skript.error("Could not find TextChannel with the provided ID, check your ID and try again.");
                    return null;
                } else {
                    Skript.error("You must login to a bot via the connect effect before you may attempt to use this expression.");
                    return null;
                }
            }else{
                Guild g = guild.getSingle(e);
                if(g.getTextChannelById(id.getSingle(e))!=null){
                    return g.getTextChannelById(id.getSingle(e));
                }
                Skript.error("Could not find TextChannel via the provided ID");
                return null;
            }
        }else{
            Skript.error("You must provide a ID to use this expression! May not leave blank.");
            return null;
        }
    }
}
