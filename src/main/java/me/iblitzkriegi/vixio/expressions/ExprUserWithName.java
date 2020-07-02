package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExprUserWithName extends SimpleExpression<User> {

    static {
        Vixio.getInstance().registerExpression(ExprUserWithName.class, User.class, ExpressionType.SIMPLE,
                "user[(1¦s)] (with [the] name|named) %string% [in %-guild%]")
                .setName("User Named")
                .setDesc("Retrieve a user by their name. If the [s] is included in users than this will return a list." +
                        "If the s is not included but their are multiple people with the same name, then the first one will be returned." +
                        "This does NOT retrieve the user from Discord. This is from what your bots can see.")
                .setUserFacing("user[s] (with [the] name|named) %string% [in %-guild%]")
                .setExample(
                        "discord command user <text>:",
                        "\ttrigger:",
                        "\t\tset {_} to user with the name arg-1 in event-guild",
                        "\t\treply with \"Oh, found them! %discord name of {_}%##%discriminator of {_}%\""
                );
    }

    private Expression<String> name;
    private Expression<Guild> guild;
    private boolean isSingle = true;

    @Override
    protected User[] get(Event e) {
        String name = this.name.getSingle(e);
        Guild guild = this.guild == null ? null : this.guild.getSingle(e);
        if (name == null) {
            return null;
        }
        List<User> userList = new ArrayList<>();
        if (guild == null) {
            Vixio.getInstance().botHashMap
                    .keySet()
                    .stream()
                    .forEach(jda -> userList.addAll(jda.getUsersByName(name, false)));
        } else {
            userList.addAll(
                    guild.getMembersByName(name, false)
                            .stream()
                            .map(Member::getUser)
                            .collect(Collectors.toList())
            );
        }
        if (userList.isEmpty()) {
            return null;
        }

        if (this.isSingle) {
            return new User[]{userList.get(0)};
        } else {
            return userList.toArray(new User[userList.size()]);
        }
    }

    @Override
    public boolean isSingle() {
        return this.isSingle;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "user" + (this.isSingle ? "" : "s") + " named " + name.toString(e, debug) + (guild == null ? "" : " in " + guild.toString(e, debug));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (parseResult.mark == 1) {
            this.isSingle = false;
        }
        name = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
