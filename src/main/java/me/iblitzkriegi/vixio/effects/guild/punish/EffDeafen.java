package me.iblitzkriegi.vixio.effects.guild.punish;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.event.Event;

public class EffDeafen extends Effect {

    static {
        Vixio.getInstance().registerEffect(EffDeafen.class, "[<un>]deafen %members% [(with|using) %bot%]")
                .setName("Deafen user")
                .setDesc("Lets you deafen or undeafen a member/user. They must be in a voice channel for this to take effect.")
                .setExample(
                        "discord command $deaf <member>:",
                        "\ttrigger:",
                        "\t\tif voice channel of arg-1 is not set:",
                        "\t\t\treply with \"You cannot deaf or undeafen someone who is not in a voice channel\"",
                        "\t\t\tstop",
                        "\t\tif arg-1 is not guild deafened:",
                        "\t\t\tdeafen arg-1 with event-bot",
                        "\t\t\treply with \"%arg-1% is now deaf\"",
                        "\t\telse:",
                        "\t\t\tundeafen arg-1 with event-bot",
                        "\t\t\treply with \"%arg-1% is no longer deafened\""
                );
    }

    private Expression<Member> members;
    private Expression<Bot> bot;
    private boolean newState;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        bot = (Expression<Bot>) exprs[1];
        newState = parseResult.regexes.size() == 0;
        return true;
    }

    @Override
    protected void execute(Event e) {
        Bot bot = this.bot.getSingle(e);
        if (bot == null) {
            return;
        }

        for (Member member : members.getAll(e)) {
            Guild guild = Util.bindGuild(bot, member.getGuild());
            if (guild != null) {
                guild.deafen(member, newState).queue();
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return (newState ? "" : "un") + "deafen " + members.toString(e, debug);
    }

}
