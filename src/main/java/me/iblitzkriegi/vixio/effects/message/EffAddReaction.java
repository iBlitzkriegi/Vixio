package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emoji;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

public class EffAddReaction extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffAddReaction.class, "add %emojis% to %message% [(with|as) %bot/string%]")
                .setName("Add reaction")
                .setDesc("Add a reaction to a message as a bot.")
                .setExample(
                        "on guild message receive:",
                        "\tadd reaction \"smile\" to event-message"
                );
    }
    private Expression<Emoji> emojis;
    private Expression<Message> message;
    private Expression<Object> bot;
    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Message message = this.message.getSingle(e);
        Emoji[] emojis = this.emojis.getAll(e);
        if (bot == null || message == null || emojis == null) {
            return;
        }

        if (Util.botIsConnected(bot, message.getJDA())) {
            for (Emoji emoji : emojis) {
                if (emoji.isEmote()) {
                    message.addReaction(emoji.getEmote()).queue();
                } else {
                    message.addReaction(emoji.getName()).queue();
                }
            }
            return;
        }

        TextChannel channel = Util.bindChannel(bot, message.getTextChannel());
        for (Emoji emoji : emojis) {
            if (emoji.isEmote()) {
                channel.addReactionById(message.getId(), emoji.getEmote()).queue();
            } else {
                channel.addReactionById(message.getId(), emoji.getName()).queue();
            }
        }



    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add " + emojis.toString(e, debug) + " to " + message.toString(e, debug) + " as " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        emojis = (Expression<Emoji>) exprs[0];
        message = (Expression<Message>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        return true;
    }
}
