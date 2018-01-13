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

public class EffBanUser extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffBanUser.class, "ban %users/strings% from %guild% [(due to|with reason|because of) %-string%] [and delete %-number% days [worth] of messages] [(with|as)] [%bot/string%]")
                .setName("Ban user")
                .setDesc("Ban either a Object, a Member, or a user by their ID")
                .setExample(
                        "command /ban <text>:",
                        "\ttrigger:",
                        "\t\tban arg-1 from guild with id \"622156151\" due to \"Not following discord rules\" as \"Jewel\""
                );
    }
    private Expression<Object> users;
    private Expression<Guild> guild;
    private Expression<Object> bot;
    private Expression<Number> days;
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
        Number days = this.days == null ? 0 : this.days.getSingle(e);
        String reason = this.reason == null ? null : this.reason.getSingle(e);
        isConnected = Util.botIsConnected(bot, guild.getJDA());
        if (isConnected) {
            for (Object object : users) {
                String user = object instanceof User ? ((User) object).getId() : (String) object;
                try {
                    guild.getController().ban(user, days.intValue(), reason).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "ban user", x.getPermission().getName());
                } catch (IllegalArgumentException x){

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
                guild.getController().ban(user, days.intValue(), reason).queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "ban user", x.getPermission().getName());
            }
        }


    }

    @Override
    public String toString(Event e, boolean debug) {
        return "ban";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        users = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        reason = (Expression<String>) exprs[2];
        days = (Expression<Number>) exprs[3];
        bot = (Expression<Object>) exprs[4];
        return true;
    }
}
