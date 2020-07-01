package me.iblitzkriegi.vixio.effects.guild.invites;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.guild.invite.ExprRetrievedInvites;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EffRetrieveInvites extends AsyncEffect {

    static {
        Vixio.getInstance().registerEffect(EffRetrieveInvites.class, "(grab|retrieve) the invite(s| link[s]) of %guild/channel%")
                .setName("Retrieve Invites of")
                .setDesc("Retrieve invites in a Guild or a Channel in a Guild. Must use the last retrieved invites expression to get them after calling this.")
                .setExample(
                        "discord command test:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tretrieve the invites of event-guild",
                        "\t\treply with \"%last grabbed invites%\""
                );
    }

    private Expression<Object> object;

    @Override
    protected void execute(Event e) {
        ExprRetrievedInvites.lastRetrievedInvites = null;
        Object input = this.object.getSingle(e);
        if (input == null) {
            return;
        }

        if (!(input instanceof Guild || input instanceof GuildChannel)) {
            return;
        }

        if (input instanceof Guild) {
            Guild guild = (Guild) input;
            try {
                List<Invite> invites = guild.retrieveInvites().complete(true);
                ExprRetrievedInvites.lastRetrievedInvites = invites.stream()
                        .map(me.iblitzkriegi.vixio.util.wrapper.Invite::parseInvite)
                        .collect(Collectors.toList());
            } catch (RateLimitedException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to retrieve invites from a guild but was rate limited.");
            } catch (PermissionException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to perform action \"retrieve invites\" but was missing the " + x.getPermission().getName() + " permission.");
            }
            return;

        }
        GuildChannel guildChannel = (GuildChannel) input;
        try {
            List<Invite> invites = guildChannel.retrieveInvites().complete(true);
            ExprRetrievedInvites.lastRetrievedInvites = invites.stream()
                    .map(me.iblitzkriegi.vixio.util.wrapper.Invite::parseInvite)
                    .collect(Collectors.toList());
            return;
        } catch (RateLimitedException e1) {
            Vixio.getErrorHandler().warn("Vixio attempted to retrieve invites from a guild channel but was rate limited.");
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "retrieve invites of " + object.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        return true;
    }
}
