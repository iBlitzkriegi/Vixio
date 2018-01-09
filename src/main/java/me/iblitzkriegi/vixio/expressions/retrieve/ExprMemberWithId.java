package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class ExprMemberWithId extends SimpleExpression<Member> {
    static {
        Vixio.getInstance().registerExpression(ExprMemberWithId.class, Member.class, ExpressionType.SIMPLE,
                "member with id %string% [in %guild%]")
                .setName("Member with ID")
                .setDesc("Get a Member via their ID, plain and simple.")
                .setExample("Coming Soon!");
    }
    private Expression<String> id;
    private Expression<Guild> guild;
    @Override
    protected Member[] get(Event e) {
        if(guild.getSingle(e) == null){
            Skript.error("You must include a guild to get the member from!");
            return null;
        }
        Guild guild = this.guild.getSingle(e);
        if(id.getSingle(e) == null){
            Skript.error("You must include a ID to find the member by!");
            return null;
        }
        String id = this.id.getSingle(e);
        if(guild.getMemberById(id) == null){
            Skript.error("Could not find member with that ID in the provided Guild");
            return null;
        }
        try {
            return new Member[]{guild.getMemberById(id)};
        }catch (IllegalArgumentException x){
            Skript.error("You must include a ID to find the member by!");
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "member with id " + id.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
