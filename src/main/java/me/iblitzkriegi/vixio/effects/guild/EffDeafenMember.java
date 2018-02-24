package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffDeafenMember extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffDeafenMember.class,
                "deafen %members% [(with|as)] [%bot/string%]", "undeafen %members% [(with|as)] [%bot/string%]")
                .setName("Deafen member")
                .setDesc("Deafen or un-deafen a member. They must be in a voice channel to see this and the provided bot must have enough permission to modify their deafened state.")
                .setExample(
                        "command /deafen <text> <text>:",
                        "\ttrigger:",
                        "\t\tset {user} to member of user with id arg-1 in guild with id arg-2",
                        "\t\tdeafen {user} as \"Jewel\""
                );
    }

    private Expression<Member> member;
    private Expression<Object> bot;
    private boolean toDeaf;

    @Override
    protected void execute(Event e) {
        boolean deafen = toDeaf;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            Vixio.getErrorHandler().cantFindBot((String) this.bot.getSingle(e), "deafen member");
            return;
        }

        Member[] members = this.member.getAll(e);
        if (member == null) {
            return;
        }

        for (Member member : members) {
            try {
                Guild bindedGuild = Util.bindGuild(bot, member.getGuild());
                if (bindedGuild != null) {
                    bindedGuild.getController().setDeafen(member, deafen).queue();
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "deafen member", x.getPermission().getName());
            }
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return (toDeaf ? "un" : "") + "deafen " + member.toString(e, debug) + " as " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        member = (Expression<Member>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        toDeaf = matchedPattern == 0;
        return true;
    }
}
