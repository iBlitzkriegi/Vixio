package me.iblitzkriegi.vixio.effects.guild.punish;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class EffMute extends Effect {

    //TODO require a bot in this and the mute effect?

    static {
        Vixio.getInstance().registerEffect(EffDeafen.class, "[<un>]mute %members%");
    }

    private Expression<Member> members;
    private boolean newState;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        newState = parseResult.regexes.size() == 0;
        return true;
    }

    @Override
    protected void execute(Event e) {
        for (Member member : members.getAll(e)) {
            member.getGuild().getController().setMute(member, newState).queue();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return (newState ? "" : "un") + "mute " + members.toString(e, debug);
    }

}
