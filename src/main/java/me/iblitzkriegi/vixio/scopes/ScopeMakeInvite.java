package me.iblitzkriegi.vixio.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Invite;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ScopeMakeInvite extends EffectSection {

    public static Invite lastInvite;

    static {
        Vixio.getInstance().registerCondition(ScopeMakeInvite.class,
                "(make|create) [a[n]] invite to %channel% [(with|as) %bot/string%]")
                .setName("Create Invite to")
                .setDesc("Create an invitation to a channel within a guild.")
                .setExample(
                        "discord command invite:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tcreate invite to event-channel:",
                        "\t\t\tset the max usage of the invite to 1",
                        "\t\treply with \"Done!\""
                );
    }

    private Expression<Object> object;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Invite invite = this.lastInvite == null ? new Invite() : this.lastInvite;
        this.lastInvite = invite;
        runSection(e);
        Object object = this.object.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (object == null || bot == null) {
            return;
        }
        if (!(object instanceof GuildChannel)) {
            return;
        }
        GuildChannel boundChannel = Util.bindChannel(bot, (GuildChannel) object);
        if (boundChannel == null) {
            return;
        }
        try {
            net.dv8tion.jda.api.entities.Invite invite2;
            invite2 = boundChannel.createInvite()
                    .setUnique(invite.isUnique())
                    .setMaxUses(invite.getMaxUses())
                    .setMaxAge(invite.getMaxAge())
                    .setTemporary(invite.isTemporary())
                    .complete();
            invite.setTimeCreated(invite2.getTimeCreated());
            invite.setUrl(invite2.getUrl());
            invite.setCode(invite2.getCode());
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "create invite to channel", x.getPermission().getName());
        }


    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create invite to " + object.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition())
            return false;
        if (!hasSection()) {
            Skript.error("An embed creation scope is useless without any content!");
            return false;
        }
        object = (Expression<Object>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        loadSection(true);
        return true;
    }
}
