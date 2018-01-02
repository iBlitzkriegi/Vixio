package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

import java.lang.reflect.Field;

public class ExprTitleUrlOfEmbed extends SimplePropertyExpression<EmbedBuilder, String> {

    private static Field URL_FIELD = null;

    static {
        try {
            URL_FIELD = EmbedBuilder.class.getDeclaredField("url");
            URL_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Skript.error("Vixio was unable to find EmbedBuilder's url field");
        }
        Vixio.getInstance().registerPropertyExpression(ExprTitleUrlOfEmbed.class, String.class,
                "[title] url[s]", "embedbuilders", "[embed[s]]")
                .setName("Title Url of Embed")
                .setDesc("Returns the url of an embed's title. Can be set to any valid https/http url. (e.g. \"https://i.imgur.com/TQgR2hW.jpg\")")
                .setExample("set url of {_embed} to \"https://i.imgur.com/TQgR2hW.jpg\"");
    }


    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return URL_FIELD != null;
    }

    @Override
    public String convert(final EmbedBuilder embed) {
        try {
            return (String) URL_FIELD.get(embed);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    String.class
            };
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                try {
                    URL_FIELD.set(embed, null);
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                return;

            case SET:
                try {
                    URL_FIELD.set(embed, delta[0]);
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "url of embed";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the url of " + getExpr().toString(e, debug);
    }

}
