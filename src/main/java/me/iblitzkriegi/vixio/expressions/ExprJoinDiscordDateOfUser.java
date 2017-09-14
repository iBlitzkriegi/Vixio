package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.MiscUtil;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 2/11/2017.
 */
@ExprAnnotation.Expression(
        name = "JoinDiscorddate",
        title = "Join Discord date of User",
        desc = "Get the date in which a user joined Discord, MM/DD/YY",
        syntax = "join discord date of user %user%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprJoinDiscordDateOfUser extends SimpleExpression<String> {
    Expression<User> vUser;
    @Override
    protected String[] get(Event e) {
        return new String[]{getDate(e)};
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<User>) expressions[0];
        return true;
    }
    private String getDate(Event e){
        User u = vUser.getSingle(e);

        String dcjoindate = String.valueOf(MiscUtil.getCreationTime(u.getIdLong()).getMonthValue()) + "-" + String.valueOf(MiscUtil.getCreationTime(u.getIdLong()).getDayOfMonth()) + "-" + String.valueOf(MiscUtil.getCreationTime(u.getIdLong()).getYear());
        return dcjoindate;
    }
}
