package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Blitz on 2/7/2017.
 */
@ExprAnnotation.Expression(
        name = "InvitesInGuild",
        title = "Invites in Guild",
        desc = "Get the Invites in a Guild, loopable",
        syntax = "invites in guild [with id] %string%",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprInvitesInGuild extends SimpleExpression<String>{
    Expression<String> vGuild;
    List<String> vInvs = new ArrayList<>();
    @Override
    protected String[] get(Event e) {
        return getStrings(e).toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vGuild = (Expression<String>) expressions[0];
        return true;
    }
    private List<String> getStrings(Event e){
        if(vInvs!=null){
            vInvs.clear();
        }
        try {
            for (Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()) {
                if (jda.getValue().getGuildById(vGuild.getSingle(e)) != null) {
                    for (Invite inv : jda.getValue().getGuildById(vGuild.getSingle(e)).getInvites().complete()) {
                        vInvs.add("https://discord.gg/" + inv.getCode());
                    }
                }
            }
            if (vInvs != null) {
                return vInvs;
            }
            Skript.warning("Guild had no Invites");
        }catch (PermissionException x){
            Skript.warning("Bot does not have enough permissions to get the invites of the specified Guild. Needs MANAGE_SERVER");
        }
        return null;
    }
}
