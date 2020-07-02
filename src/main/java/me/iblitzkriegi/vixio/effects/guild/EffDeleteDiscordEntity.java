package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffDeleteDiscordEntity extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffDeleteDiscordEntity.class, "delete discord entit(y|ies) %messages/channels/roles/guilds/categories/emote% [with %bot/string%]")
                .setName("Delete Discord Entity")
                .setDesc("Delete any deletable discord entity")
                .setExample("delete discord entity event-channel");
    }

    private Expression<Object> bot;
    private Expression<Object> discordEntities;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return;
        }
        String type = "";
        try {
            for (Object object : this.discordEntities.getAll(e)) {
                if (object instanceof UpdatingMessage) {
                    type = "message";
                    Util.bindMessage(bot, ((UpdatingMessage) object).getMessage()).queue(message1 -> message1.delete().queue());
                } else if (object instanceof GuildChannel) {
                    type = "channel";
                    GuildChannel guildChannel = Util.bindChannel(bot, (GuildChannel) object);
                    if (guildChannel != null) {
                        guildChannel.delete().queue();
                    }
                } else if (object instanceof Role) {
                    type = "role";
                    Role boundRole = bot.getJDA().getRoleById(((Role) object).getId());
                    if (boundRole != null) {
                        boundRole.delete().queue();
                    }
                } else if (object instanceof Guild) {
                    type = "guild";
                    Guild boundGuild = Util.bindGuild(bot, (Guild) object);
                    if (boundGuild != null) {
                        boundGuild.delete().queue();
                    }
                } else if (object instanceof Category) {
                    type = "category";
                    Category boundCategory = bot.getJDA().getCategoryById(((Category) object).getId());
                    if (boundCategory != null) {
                        boundCategory.delete().queue();
                    }
                } else if (object instanceof Emote) {
                    type = "emote";
                    Emote emote = (Emote) object;
                    emote.getGuild().getEmoteById(emote.getID()).delete();
                }
            }
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "delete " + type, x.getPermission().getName());
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "delete discord entity " + discordEntities.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        discordEntities = (Expression<Object>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}
