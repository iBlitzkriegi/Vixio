package me.iblitzkriegi.vixio.effects.guild.punish;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class EffMute extends Effect {

    static {
        Vixio.getInstance().registerEffect(EffMute.class, "[<un>]mute %members% [(with|using) %bot%]")
                .setName("Mute")
                .setName("Lets you mute/unmute a user/member")
                .setExample("mute event-user in event-guild with event-bot",
                        "unmute event-member with event-bot"
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
                guild.getController().setMute(member, newState).queue();
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return (newState ? "" : "un") + "mute " + members.toString(e, debug);
    }

}
