package me.iblitzkriegi.vixio.conditions.bot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.vCondShowroom;
import static me.iblitzkriegi.vixio.util.VixioAudioHandlers.getGuildAudioPlayer;

/**
 * Created by Blitz on 12/17/2016.
 */
public class CondBotIsPlayingTrack extends Condition{
    static {
        Documentation docs = Vixio.registerCondition(CondBotIsPlayingTrack.class, "bot %string% is playing audio in guild %string%", "bot %string% is not playing audio in guild %string%")
                .setName("Bot is playing audio")
                .setDesc("Check if the bot is or is not playing audio in a certain Guild")
                .setExample("Coming soon");
        String t = docs.getName().replaceAll(" ", "");
        vCondShowroom.put(t, docs.getName());
        VixioAnnotationParser.vCondSyntax.put(docs.getName().replaceAll(" ", ""), docs.getSyntaxes()[0]);
        VixioAnnotationParser.vCondExample.put(docs.getName().replaceAll(" ", ""), docs.getExample());
        VixioAnnotationParser.vCondDesc.put(docs.getName().replaceAll(" ", ""), docs.getDesc());
    }
    Expression<String> vBot;
    Expression<String> vGuild;
    private boolean not;
    @Override
    public boolean check(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            Boolean t = musicManager.player.getPlayingTrack()!=null ? true : false;
            if((not && t) || (not && !t)){
                return (not == t);
            }else if((!not && t) || (!not && !t)){
                return (not == t);
            }

        }else{
            Skript.warning("Could not find Guild via that ID");
        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        not = i == 0;
        return true;
    }
}
