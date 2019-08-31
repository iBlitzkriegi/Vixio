package me.iblitzkriegi.vixio.expressions.embeds.properties;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprNewEmbedFooter extends SimpleExpression<MessageEmbed.Footer> {
    static {
        Vixio.getInstance().registerExpression(ExprNewEmbedFooter.class, MessageEmbed.Footer.class, ExpressionType.COMBINED,
                "[a] footer with [the] text %string%[( and [the]|, )(icon %-string%|no icon)]")
                .setName("New Footer")
                .setDesc("Returns a footer with the specified data")
                .setExample("set footer of {_embed} to a footer with text \"Hi Pika\" and icon \"https://i.imgur.com/TQgR2hW.jpg\"");
    }

    private Expression<String> text;
    private Expression<String> icon = null;

    @Override
    protected MessageEmbed.Footer[] get(Event e) {
        EmbedBuilder builder;
        try {
            builder = new EmbedBuilder().setFooter(text.getSingle(e), (icon == null ? null : icon.getSingle(e)));
        } catch (IllegalArgumentException e1) {
            Skript.error("Vixio encountered the error \"" + e1.getMessage() + "\" while trying to make " + this.toString(e, false) + " with the icon \"" + icon.getSingle(e) + "\"");
            return null;
        }
        return new MessageEmbed.Footer[]{
                builder.isEmpty() ? null : builder.build().getFooter()
        };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MessageEmbed.Footer> getReturnType() {
        return MessageEmbed.Footer.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "footer with text " + text.toString(e, debug) + (icon == null ? " and no icon" : " and icon " + icon.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        text = (Expression<String>) exprs[0];
        icon = (Expression<String>) exprs[1];
        return true;
    }
}
