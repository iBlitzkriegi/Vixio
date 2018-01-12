package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprEmbedOfBuilder extends SimplePropertyExpression<MessageBuilder, MessageEmbed> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmbedOfBuilder.class, MessageEmbed.class, "embed", "messagebuilders")
                .setName("Embed of Message Builder")
                .setDesc("Get the Embed of a Message Builder")
                .setExample("Coming Soon!");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
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
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{MessageEmbed.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
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
    public String toString(final Event e, final boolean debug) {
        return "the embed of " + getExpr().toString(e, debug);
    }
}
