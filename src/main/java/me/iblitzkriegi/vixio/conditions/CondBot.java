package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class CondBot extends Condition implements EasyMultiple<User, Void> {

    static {
        Vixio.getInstance().registerCondition(CondBot.class, "%users% (is|are) bot[s]", "%users% (is|are)(n't| not) bot[s]")
                .setName("User is Bot")
                .setDesc("Let's you check if a user is a bot account.")
                .setExample("discord command bot:",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\tif event-user is bot:",
                        "\t\t\treply with \"Omg I love bots!\""
                );
    }

    private Expression<User> users;

    @Override
    public boolean check(Event e) {
        return check(users.getAll(e), o -> o.isBot(), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return users.toString(e, debug) + " is " + (isNegated() ? " not a " : "") + "bot";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        users = (Expression<User>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}
