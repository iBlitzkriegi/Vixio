package me.iblitzkriegi.vixio.expressions.embeds.properties;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprNewEmbedField extends SimpleExpression<MessageEmbed.Field> {

    static {
        Vixio.getInstance().registerExpression(ExprNewEmbedField.class, MessageEmbed.Field.class, ExpressionType.COMBINED,
                "[(a|the)] (split|inline) field[s] [named %-string%] [with [the] value %-string%]", "[(a|the)] field[s] [named %-string%] [with [the] value %-string%]")
                .setName("New Field")
                .setDesc("Returns a field with the specified data")
                .setExample("set field of {_embed} to a split field named \"Super cool field\" and value \"Super cool value\"");
    }

    private boolean split;
    private Expression<String> name;
    private Expression<String> value;

    @Override
    protected MessageEmbed.Field[] get(Event e) {
        EmbedBuilder embed = new EmbedBuilder();
        if (name == null && value == null) {

            embed.addBlankField(split);

        } else {

            String name = this.name == null ? EmbedBuilder.ZERO_WIDTH_SPACE : this.name.getSingle(e);
            String value = this.value == null ? EmbedBuilder.ZERO_WIDTH_SPACE : this.value.getSingle(e);

            if (name == null || value == null) return null;

            embed.addField(name, value, split);

        }


        return new MessageEmbed.Field[]{
                embed.isEmpty() ? null : embed.getFields().get(0)
        };

    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MessageEmbed.Field> getReturnType() {
        return MessageEmbed.Field.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "a field" + (split ? "split " : "") + (name == null ? "" : " with the name " + name.toString(e, debug)) + (value == null ? "" : " and value " + value.toString(e, debug));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        value = (Expression<String>) exprs[1];
        split = matchedPattern == 0;
        return true;
    }
}
