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

public class EffUnbanUser extends Effect{
    static {
        Vixio.getInstance().registerEffect(EffUnbanUser.class, "unban %users/strings% from %guild% [(with|as) %bot/string%]")
                .setName("Unban user from Guild")
                .setDesc("Un-ban a user from a guild as a bot.")
                .setExample(
                        "command /ban <text>:",
                        "\ttrigger:",
                        "\t\tunban arg-1 from guild with id \"622156151\" due to \"Not following discord rules\" as \"Jewel\"");
    }
    private Expression<Object> users;
    private Expression<Guild> guild;
    private Expression<Object> bot;
    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        Object[] users = this.users.getAll(e);
        Guild bindedGuild = Util.bindGuild(bot, guild);
        if (users == null || guild == null || bot == null || bindedGuild == null) {
            return;
        }

        for (Object object : users) {
            try {
                String user = object instanceof User ? ((User) object).getId() : (String) object;
                bindedGuild.getController().unban(user).queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "unban user", x.getPermission().getName());
            } catch (IllegalArgumentException x) {

            }
        }
        return;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "unban " + users.toString(e, debug) + " from " + guild.toString(e, debug) + " as " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        users = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        return true;
    }
}
