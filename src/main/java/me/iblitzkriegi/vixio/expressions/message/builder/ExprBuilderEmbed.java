package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprBuilderEmbed extends SimplePropertyExpression<MessageBuilder, MessageEmbed> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderEmbed.class, MessageEmbed.class,
                "embed", "messagebuilders")
                .setName("Embed of Message Builder")
                .setDesc("Get the Embed of a Message Builder. You can set, delete and reset the embed.")
                .setExample("on guild message received:" +
                        "\tif name of event-bot contains \"Jewel\":\t" +
                        "\t\tset {_cmd::*} to split content of event-message at \" \"" +
                        "\t\tif {_cmd::1} is \"##build\": " +
                        "\t\t\tmake embed:" +
                        "\t\t\t\tadd field with value \"to remove\" to fields of the embed" +
                        "\t\t\t\tadd field with value \"to remove\" to fields of the embed # this one shoudl stay because im only removing not removing all" +
                        "\t\t\t\tadd field with value \"to stay\" to fields of the embed" +
                        "\t\t\t\tset color of the embed to red" +
                        "\t\t\tset {e} to a new message builder" +
                        "\t\t\tset text of {e} to \"Hey there \"" +
                        "\t\t\tappend \"Current text: %text of {e}%\" to {e}" +
                        "\t\t\tappend \"World!\" to {e}" +
                        "\t\t\tappend \"Appended form: %text of {e}%\" to {e}" +
                        "\t\t\tset embed of {e} to last embed" +
                        "\t\t\tappend \"Info for embed attached to this builder: %color of embed of {e}%\" to {e}" +
                        "\t\t\tsend {e} to event-channel with event-bot");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<MessageBuilder>) exprs[0]);
        return true;
    }

    @Override
    protected String getPropertyName() {
        return "embed of messagebuilders";
    }

    @Override
    public MessageEmbed convert(MessageBuilder messageBuilder) {
        return messageBuilder.isEmpty() ? null : messageBuilder.build().getEmbeds().get(0);
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{MessageEmbed.class};
        }

        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        MessageBuilder builder = getExpr().getSingle(e);
        if (builder == null) return;

        switch (mode) {
            case RESET:
            case DELETE:
                builder.setEmbed(null);
                break;
            case SET:
                MessageEmbed messageEmbed = (MessageEmbed) delta[0];
                if (!messageEmbed.isEmpty()) {
                    builder.setEmbed(messageEmbed);
                } else {
                    Vixio.getErrorHandler().warn("Vixio tried to access a empty Embed to set its title! This is not possible.");
                }
        }
    }


    @Override
    public Class<? extends MessageEmbed> getReturnType() {
        return MessageEmbed.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the embed of " + getExpr().toString(e, debug);
    }
}
