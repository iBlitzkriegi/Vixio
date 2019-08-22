package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprBanned extends SimpleExpression<User> {
    public static List<Guild.Ban> lastRetrievedBanList;

    static {
        Vixio.getInstance().registerExpression(ExprBanned.class, User.class, ExpressionType.SIMPLE, "[last] (grabbed|retrieved) bans")
                .setName("Retrieved bans")
                .setDesc("Get the last set of retrieved bans from a guild. The bot must have enough permissions to retrieve the bans list.")
                .setExample(
                        "discord command $bans:",
                        "\ttrigger:",
                        "\t\tgrab bans of event-guild",
                        "\t\treply with \"Here are the banned users: %grabbed bans%\""
                );
    }

    @Override
    protected User[] get(Event e) {
        if (lastRetrievedBanList == null) {
            return null;
        }
        ArrayList<User> users = new ArrayList<>();
        for (Guild.Ban ban : lastRetrievedBanList) {
            users.add(ban.getUser());
        }
        return users.toArray(new User[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "retrieved bans";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
