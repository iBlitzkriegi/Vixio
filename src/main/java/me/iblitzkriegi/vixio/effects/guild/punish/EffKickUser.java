package me.iblitzkriegi.vixio.effects.guild.punish;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffKickUser extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffKickUser.class, "kick %users/strings% from %guild% [(due to|with reason|because of) %-string%] [(with|as)] [%bot/string%]")
                .setName("Kick user")
                .setDesc("Kick either a user, a member, or a user by their ID")
                .setExample(
                        "command /kick <text>:",
                        "\ttrigger:",
                        "\t\tkick arg-1 from guild with id \"622156151\" due to \"Not following discord rules\" as \"Jewel\""
                );
    }
    private Expression<Object> users;
    private Expression<Guild> guild;
    private Expression<Object> bot;
    private Expression<String> reason;
    @Override
    protected void execute(Event e) {
        boolean isConnected;
        Object[] users = this.users.getAll(e);
        if (users == null) {
            return;
        }
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return;
        }
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return;
        }
        String reason = this.reason == null ? null : this.reason.getSingle(e);
        isConnected = Util.botIsConnected(bot, guild.getJDA());
        if (isConnected) {
            for (Object object : users) {
                String user = object instanceof User ? ((User) object).getId() : (String) object;
                try {
                    guild.getController().kick(user, reason).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "kick user", x.getPermission().getName());
                } catch (IllegalArgumentException x) {

                }
            }
            return;
        }
        Guild bindedGuild = bot.getJDA().getGuildById(guild.getId());
        if (bindedGuild == null) {
            Vixio.getErrorHandler().botCantFind(bot, "guild", guild.getId());
            return;
        }
        for (Object object : users) {
            String user = object instanceof User ? ((User) object).getId() : (String) object;
            try {
                guild.getController().kick(user, reason).queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "kick user", x.getPermission().getName());
            }
        }


    }

    @Override
    public String toString(Event e, boolean debug) {
        return "kick " + users.toString(e, debug) + " from " + guild.toString(e, debug) + (reason == null ? "" : " due to" + reason.toString(e, debug)) + " as " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        users = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        reason = (Expression<String>) exprs[2];
        bot = (Expression<Object>) exprs[3];
        return true;
    }
}
