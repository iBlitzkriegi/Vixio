package me.iblitzkriegi.vixio.effects.effGenerals;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/16/2016.
 */
public class EffMakeCodeblock extends Effect {
    private Expression<String> message2build;
    private Expression<String> id;
    private Expression<String> lang;
    @Override
    protected void execute(Event e) {
        if(e instanceof EvntGuildMsgReceived) {
            MessageBuilder builder = new MessageBuilder();
            String rawr2b = message2build.getSingle(e).replaceAll(":::", "\n");
            if(lang!=null) {
                builder.appendString("```" + lang.getSingle(e)).appendString("\n");
            }else{
                builder.appendString("```").appendString("\n");
            }
            builder.appendString(rawr2b).appendString("\n");
            builder.appendString("```").appendString("\n");
            getAPI().getJDA().getTextChannelById(id.getSingle(e)).sendMessage(builder.build());
        }else if(e instanceof EvntPrivateMessageReceived){
            MessageBuilder builder = new MessageBuilder();
            String rawr2b = message2build.getSingle(e).replaceAll(":::", "\n");
            builder.appendString("```md").appendString("\n");
            builder.appendString(rawr2b).appendString("\n");
            builder.appendString("```").appendString("\n");
            User author = getAPI().getJDA().getUserById(((EvntPrivateMessageReceived)e).getEvntAuthor().getId());
            author.getPrivateChannel().sendMessage(builder.build());
        }else{
            MessageBuilder builder = new MessageBuilder();
            String rawr2b = message2build.getSingle(e).replaceAll(":::", "\n");
            if(lang!=null) {
                builder.appendString("```" + lang.getSingle(e)).appendString("\n");
            }else{
                builder.appendString("```").appendString("\n");
            }
            builder.appendString(rawr2b).appendString("\n");
            builder.appendString("```").appendString("\n");
            getAPI().getJDA().getTextChannelById(id.getSingle(e)).sendMessage(builder.build());
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(expr[2]!=null) {
            message2build = (Expression<String>) expr[0];
            lang = (Expression<String>) expr[1];
            id = (Expression<String>) expr[2];
        }else{
            message2build = (Expression<String>) expr[0];
            id = (Expression<String>) expr[1];
            lang = null;
        }
        return true;
    }
}
