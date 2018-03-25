package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprEmoji extends SimpleExpression<Emote> {
    static {
        //TODO: make this properly nullable
        Vixio.getInstance().registerExpression(ExprEmoji.class, Emote.class, ExpressionType.SIMPLE,
                "(emoji|emote|reaction)[s] %strings% [(from|in) %-guild%]")
                .setName("Emoji named")
                .setDesc("Get a emoji by its name, if its a custom emote you must include the guild.")
                .setExample(
                        "on guild message receive:",
                        "\tadd reaction \"smile\" to event-message"
                );
    }

    private Expression<String> name;
    private Expression<Guild> guild;

    @Override
    protected Emote[] get(Event e) {
        String[] emote = name.getAll(e);
        Guild guild = this.guild == null ? null : this.guild.getSingle(e);
        if (emote == null) {
            return null;
        }

        List<Emote> emojis = new ArrayList<>();
        for (String input : emote) {
            emojis.add(Util.unicodeFrom(input, guild));
        }

        if (emojis.isEmpty()) {
            return null;
        }

        return emojis.toArray(new Emote[emojis.size()]);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Emote> getReturnType() {
        return Emote.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "emoji named " + name.toString(e, debug) + (guild == null ? "" : " from " + guild.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
