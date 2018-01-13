package me.iblitzkriegi.vixio.expressions.guild.controller;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class ExprMemberIsGuildDeaf extends SimpleExpression<Boolean> {
    static {
        Vixio.getInstance().registerExpression(ExprMemberIsGuildDeaf.class, Boolean.class, ExpressionType.SIMPLE, "guild deafened state of %member%")
                .setName("Deafened state of member in guild")
                .setDesc("Get the deafened state of a member in a guild. If they are deafened by someone then this returns true. This will not be updated unless a user is in a voice channel when they are deafened.")
                .setExample(
                        "on guild message receive:",
                        "\tset {e} to member of event-user in event-guild",
                        "\treply with \"%guild deafen state of {e} in event-guild%\""
                );
    }
    private Expression<Member> member;
    @Override
    protected Boolean[] get(Event e) {
        Member member = this.member.getSingle(e);
        if (member == null) return null;

        return new Boolean[]{member.getVoiceState().isGuildDeafened()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "guild deafened state of " + member.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        member = (Expression<Member>) exprs[0];
        return true;
    }
}
