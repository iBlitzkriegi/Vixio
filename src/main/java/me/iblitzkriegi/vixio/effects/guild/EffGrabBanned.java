package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.guild.ExprBanned;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import java.util.List;

public class EffGrabBanned extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffGrabBanned.class, "(retrieve|grab) [the] (bans|ban list) of %guild%")
                .setName("Bans list")
                .setDesc("Retrieve all of the banned users in a guild. The results are stored in the retrieved bans expression")
                .setExample(
                        "discord command $bans:",
                        "\ttrigger:",
                        "\t\tgrab bans of event-guild",
                        "\t\tset {_errors} to the last vixio error",
                        "\t\tif {_errors} is set:",
                        "\t\t\treply with \"I ran into an error! %{_errors}%\"",
                        "\t\t\tstop",
                        "\t\treply with \"Here are the current bans: `%grabbed bans%`\""
                );
    }

    private Expression<Guild> guild;

    @Override
    protected void execute(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild != null) {
            try {
                ExprBanned.lastRetrievedBanList = null;
                List<Guild.Ban> banList = guild.retrieveBanList().complete(true);
                if (banList != null) {
                    ExprBanned.lastRetrievedBanList = banList;
                }
            } catch (RateLimitedException e1) {
                Vixio.getErrorHandler().warn("Vixio attempted to get the ban list of a guild but was ratelimited.");
            } catch (PermissionException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to get the ban list of a guild but was missing the required permissions.");
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "ban list of " + guild.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guild = (Expression<Guild>) exprs[0];
        return true;
    }
}
