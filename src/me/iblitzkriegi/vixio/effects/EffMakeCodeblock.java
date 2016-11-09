package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] (make|create) codeblock %string% for %string% with %string% [with lang %-string%]")
public class EffMakeCodeblock extends Effect {
    Expression<String> vCodeblock;
    Expression<String> vChannel;
    Expression<String> vBot;
    Expression<String> vLang;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        for(TextChannel tc : jda.getTextChannels()){
            if(tc.getId().equalsIgnoreCase(vChannel.getSingle(e))){
                MessageBuilder builder = new MessageBuilder();
                String vToBuild = vCodeblock.getSingle(e).replaceAll(":::", "\n");
                if(vLang!=null){
                    builder.appendString("```" + vLang.getSingle(e)).appendString("\n");
                }else{
                    builder.appendString("```").appendString("\n");
                }
                builder.appendString(vToBuild).appendString("\n");
                builder.appendString("```").appendString("\n");
                tc.sendMessage(builder.build());
            }
        }
        for(User tc : jda.getUsers()) {
            if (tc.getId().equalsIgnoreCase(vChannel.getSingle(e))) {
                MessageBuilder builder = new MessageBuilder();
                String vToBuild = vCodeblock.getSingle(e).replaceAll(":::", "\n");
                if (vLang != null) {
                    builder.appendString("```" + vLang.getSingle(e)).appendString("\n");
                } else {
                    builder.appendString("```").appendString("\n");
                }
                builder.appendString(vToBuild).appendString("\n");
                builder.appendString("```").appendString("\n");
                tc.getPrivateChannel().sendMessage(builder.build());
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(expr[3]==null) {
            vCodeblock = (Expression<String>) expr[0];
            vChannel = (Expression<String>) expr[1];
            vBot = (Expression<String>) expr[2];
        }else{
            vCodeblock = (Expression<String>) expr[0];
            vChannel = (Expression<String>) expr[1];
            vBot = (Expression<String>) expr[2];
            vLang = (Expression<String>) expr[3];
        }

        return true;
    }
}
