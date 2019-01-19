package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.List;

public class ExprEmbedFields extends SimpleExpression<MessageEmbed.Field> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmbedFields.class, MessageEmbed.Field.class, "field", "embedbuilders")
                .setName("Fields Of Embed")
                .setDesc("Returns a embed's fields")
                .setExample("set {_fields::*} to fields of {_embed}");
    }

    private Expression<EmbedBuilder> builder;

    @Override
    protected MessageEmbed.Field[] get(Event e) {
        EmbedBuilder builder = this.builder.getSingle(e);
        if (builder == null) return null;

        List<MessageEmbed.Field> fields = builder.getFields();
        return fields.toArray(new MessageEmbed.Field[fields.size()]);
    }

    @Override
    public Class<? extends MessageEmbed.Field> getReturnType() {
        return MessageEmbed.Field.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fields of " + builder.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        builder = (Expression<EmbedBuilder>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE_ALL) && builder.isSingle()) {
            return new Class[]{
                    MessageEmbed.Field[].class
            };
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {

        EmbedBuilder embed = builder.getSingle(e);
        if (embed == null) {
            return;
        }

        switch (mode) {

            case RESET:
            case DELETE:
                embed.clearFields();
                break;

            case SET:
                embed.clearFields();

            case ADD:
                for (int i = 0; i < delta.length; i++)
                    embed.addField((MessageEmbed.Field) delta[i]);
                break;

            case REMOVE_ALL:
                embed.getFields().removeAll(Arrays.asList(delta));
                break;
            case REMOVE:
                for (int i = 0; i < delta.length; i++)
                    embed.getFields().remove(delta[i]);

        }

    }


}
