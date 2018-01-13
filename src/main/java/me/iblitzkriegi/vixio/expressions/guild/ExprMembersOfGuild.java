package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class ExprMembersOfGuild extends SimpleExpression<Member> {
    static {
        Vixio.getInstance().registerExpression(ExprMembersOfGuild.class, Member.class, ExpressionType.SIMPLE, "members of %guild%")
                .setName("Members of Guild")
                .setDesc("Get all of the text channels in a guild.")
                .setExample(
                        "on guild message receive:",
                        "\tset {channels::*} to members of event-guild",
                        "\tloop {channels::*}:",
                        "\t\tbroadcast \"%name of loop-value%\""
                );
    }
    private Expression<Guild> guild;
    @Override
    protected Member[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return null;
        }
        return guild.getMembers().toArray(new Member[guild.getMembers().size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "members of " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guild = (Expression<Guild>) exprs[0];
        return true;
    }
}
