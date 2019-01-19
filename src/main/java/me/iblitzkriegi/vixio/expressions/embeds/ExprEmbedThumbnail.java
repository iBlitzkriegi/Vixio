package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed.Thumbnail;
import org.bukkit.event.Event;

public class ExprEmbedThumbnail extends SimplePropertyExpression<EmbedBuilder, Thumbnail> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmbedThumbnail.class, Thumbnail.class,
                "(thumbnail|icon)", "embedbuilders")
                .setName("Thumbnail of Embed")
                .setDesc("Returns the thumbnail of an embed. Can be set to any string (e.g. https://i.imgur.com/TQgR2hW.jpg).")
                .setExample("set the thumbnail of {_embed} to \"https://i.imgur.com/TQgR2hW.jpg\"");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public Thumbnail convert(EmbedBuilder embed) {
        return embed.isEmpty() ? null : embed.build().getThumbnail();
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    String.class
            };
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) {
            return;
        }

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setThumbnail(null);
                return;

            case SET:
                String url = (String) delta[0];
                try {
                    embed.setThumbnail(url);
                } catch (IllegalArgumentException e1) {
                    Skript.error("Vixio encountered the error \"" + e1.getMessage() + "\" while trying to set the thumbnail of " + getExpr().toString(e, false) + " to " + "\"" + url + "\"");
                }
        }
    }

    @Override
    public Class<? extends Thumbnail> getReturnType() {
        return Thumbnail.class;
    }

    @Override
    protected String getPropertyName() {
        return "thumbnail of embed";
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the thumbnail of " + getExpr().toString(e, debug);
    }

}
