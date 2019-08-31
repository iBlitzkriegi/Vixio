package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.HashMap;

public class EffCreateInvite extends AsyncEffect {
    public static HashMap<GuildChannel, Invite> guildInviteHashMap = new HashMap<>();
    public static Invite lastCreatedInvite;

    static {
        Vixio.getInstance().registerEffect(EffCreateInvite.class, "create a[n] (invite|invitation) to %channel% [with %bot/string%] [and store it in %-objects%]")
                .setName("Create Invite to Guild")
                .setDesc("Create a invitation to a channel. Can store the newly created invite with the storage option or use the last created invite expression.")
                .setExample(
                        "discord command invite:",
                        "\ttrigger:",
                        "\t\tcreate an invite to event-channel",
                        "\t\treply with \"%the last created invite%\""
                );
    }

    private Expression<GuildChannel> channel;
    private Expression<Object> bot;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        GuildChannel channel = this.channel.getSingle(e);
        if (bot == null || channel == null) {
            return;
        }
        try {
            Invite invite = channel.createInvite().complete();
            if (varExpr != null) {
                Util.storeInVar(varName, varExpr, invite.getUrl(), e);
            }
            lastCreatedInvite = invite;
            guildInviteHashMap.put(channel, invite);

        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "create invite", x.getPermission().getName());
        }
    }


    @Override
    public String toString(Event e, boolean debug) {
        return "create invite to " + channel.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channel = (Expression<GuildChannel>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        if (exprs[2] instanceof Variable) {
            varExpr = (Variable<?>) exprs[2];
            varName = SkriptUtil.getVariableName(varExpr);
        }
        return true;
    }
}
