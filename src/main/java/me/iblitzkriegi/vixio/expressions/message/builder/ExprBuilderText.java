package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.MessageBuilder;
import org.bukkit.event.Event;

public class ExprBuilderText extends SimplePropertyExpression<MessageBuilder, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderText.class, String.class,
                "[<stripped>] text", "messagebuilders")
                .setName("Text of a message builder")
                .setDesc("Get the text inside of a message builder. Can be either set, reset, or deleted.")
                .setExample(
                        "command /build:",
                        "\ttrigger:",
                        "\t\tset {e} to a new message builder",
                        "\t\tset text of {e} to \"Hey look at this\"",
                        "\t\tbroadcast \"%text of {e}%\""
                );
    }

    private boolean stripped;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<MessageBuilder>) exprs[0]);
        stripped = parseResult.regexes.size() == 1;
        return true;
    }

    @Override
    protected String getPropertyName() {
        return "[<stripped>] text";
    }

    @Override
    public String convert(MessageBuilder messageBuilder) {
        if (stripped) {
            return messageBuilder.isEmpty() ? null : messageBuilder.build().getContentStripped();
        } else {
            return messageBuilder.isEmpty() ? null : messageBuilder.build().getContentRaw();
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE)) {
            return new Class[]{String.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        MessageBuilder[] messageBuilders = getExpr().getAll(e);
        switch (mode) {
            case RESET:
            case REMOVE:
                for (MessageBuilder messageBuilder : messageBuilders) {
                    messageBuilder.setContent(null);
                }
                break;
            case SET:
                for (MessageBuilder messageBuilder : messageBuilders) {
                    messageBuilder.setContent((String) delta[0]);
                }
                break;
        }
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the text of " + getExpr().toString(e, debug);
    }

}
