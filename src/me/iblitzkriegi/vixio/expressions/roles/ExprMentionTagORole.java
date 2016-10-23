package me.iblitzkriegi.vixio.expressions.roles;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Role;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/21/2016.
 */
public class ExprMentionTagORole extends SimpleExpression<String> {
    private Expression<String> id;
    private Role role;
    @Override
    protected String[] get(Event e) {
        for(Guild g : getAPI().getJDA().getGuilds()){
            for(Role r : g.getRoles()){
                if(r.getId().equalsIgnoreCase(id.getSingle(e))){
                    role = r;
                }
            }
        }
        return new String[]{role.getAsMention()};
    }

    @Override
    public boolean isSingle() {
        return true;
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
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expr[0];
        return true;
    }

}
