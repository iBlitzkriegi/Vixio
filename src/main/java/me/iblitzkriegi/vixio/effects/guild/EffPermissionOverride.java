package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffPermissionOverride extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffPermissionOverride.class,
                "(allow|1¦deny) %roles/members% [the] permission[s] %permissions% [in %-channels%] [with %bot/string%]")
                .setName("Member Permission in Channel")
                .setDesc("Allow, or deny a role or a member permissions to a channel")
                .setExample(
                        "discord command grant <text> <permission>:",
                        "\ttrigger:",
                        "\t\tif id of event-guild is not \"219967335266648065\":",
                        "\t\t\tstop",
                        "\t\tset {_role} to role with id arg-1",
                        "\t\tallow {_role} the permission arg-2 in event-channel",
                        "\t\tdeny the public role of event-guild the permission arg-2 in event-channel"
                );
    }

    private Expression<Channel> channel;
    private Expression<Object> bot;
    private Expression<Permission> permissions;
    private Expression<Object> inputs;
    private boolean allow;


    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return;
        }
        Permission[] permissions = this.permissions.getAll(e);
        Object[] inputs = this.inputs.getAll(e);
        Channel[] channels = this.channel.getAll(e);
        for (Channel channel : channels) {
            for (Object input : inputs) {
                try {
                    if (input instanceof Role) {
                        Role role = (Role) input;
                        if (allow) {
                            channel.putPermissionOverride(role).setAllow(permissions).queue();
                        } else {
                            channel.putPermissionOverride(role).setDeny(permissions).queue();
                        }
                    } else if (input instanceof Member) {
                        Member member = (Member) input;
                        if (allow) {
                            channel.putPermissionOverride(member).setAllow(permissions).queue();
                        } else {
                            channel.putPermissionOverride(member).setDeny(permissions).queue();
                        }
                    }
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "manage permissions", x.getPermission().getName());
                }
            }

        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return (allow ? "allow " : "deny ") + inputs.toString(e, debug) + " the permission " + permissions.toString(e, debug) + " in " + channel.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        inputs = (Expression<Object>) exprs[0];
        permissions = (Expression<Permission>) exprs[1];
        channel = (Expression<Channel>) exprs[2];
        bot = (Expression<Object>) exprs[3];
        allow = parseResult.mark == 0;
        return true;
    }
}
