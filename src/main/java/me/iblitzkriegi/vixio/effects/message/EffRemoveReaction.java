package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffRemoveReaction extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffRemoveReaction.class, "remove %emotes% added by %user% from %message% [with %bot/string%]")
                .setName("Remove Emote by User")
                .setDesc("Remove a specific users emote from a message, this is for removing a users reacted emote in the reaction add event mostly.")
                .setExample(
                        "on reaction added:",
                        "\tremove event-emote added by event-user from event-message"
                );
    }

    private Expression<Emote> emote;
    private Expression<User> user;
    private Expression<UpdatingMessage> message;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        User user = this.user.getSingle(e);
        Message message = UpdatingMessage.convert(this.message.getSingle(e));
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (user == null || message == null || bot == null) {
            return;
        }
        Util.bindMessage(bot, message).queue(boundMessage -> {
            if (boundMessage == null) {
                return;
            }
            for (MessageReaction messageReaction : boundMessage.getReactions()) {
                for (Emote emote : this.emote.getAll(e)) {
                    if (messageReaction.getReactionEmote().getName().equalsIgnoreCase(emote.getName())) {
                        try {
                            messageReaction.removeReaction(user).queue();
                        } catch (PermissionException x) {
                            Vixio.getErrorHandler().needsPerm(bot, "remove reaction from user", x.getPermission().getName());
                        }
                    }
                }
            }
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "remove " + emote.toString(e, debug) + " added by " + user.toString(e, debug) + " from " + message.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        emote = (Expression<Emote>) exprs[0];
        user = (Expression<User>) exprs[1];
        message = (Expression<UpdatingMessage>) exprs[2];
        bot = (Expression<Object>) exprs[3];
        return true;
    }
}
