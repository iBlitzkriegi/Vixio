package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class ExprUserWithId extends SimpleExpression<User> {
    static {
        Vixio.getInstance().registerExpression(ExprUserWithId.class, User.class, ExpressionType.SIMPLE,
                "user with id %string% [in %guild%]")
                .setName("User with ID")
                .setDesc("Get a User via their ID. ")
                .setExample("Coming Soon!");
    }
    private Expression<String> id;
    private Expression<Guild> guild;
    @Override
    protected User[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return null;
        }
        String id = this.id.getSingle(e);
        if (id == null || id.isEmpty()) {
            return null;
        }
        Member member = guild.getMemberById(id);
        if (member == null) {
            return null;
        }
        return new User[]{member.getUser()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "user with id " + id.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
