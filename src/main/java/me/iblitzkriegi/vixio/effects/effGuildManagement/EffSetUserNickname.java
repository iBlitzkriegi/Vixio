package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/1/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] set nickname of user %string% to %string% with %string% in guild %string%")
public class EffSetUserNickname extends Effect {
    private Expression<String> sName;
    private Expression<String> vGuild;
    private Expression<String> vBot;
    private Expression<String> vUser;
    @Override
    protected void execute(Event e) {
        setNickname(e);
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sName = (Expression<String>) expr[1];
        vGuild = (Expression<String>) expr[3];
        vBot = (Expression<String>) expr[2];
        vUser = (Expression<String>) expr[0];
        return true;
    }

    private void setNickname(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        Guild vG = jda.getGuildById(vGuild.getSingle(e));
        User user = jda.getUserById(vUser.getSingle(e));
        Member m = vG.getMember(user);
        vG.getController().setNickname(m, sName.getSingle(e)).queue();
    }

}
