package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.MessageBuilder;
import org.bukkit.event.Event;

public class ExprBuilderText extends ChangeableSimplePropertyExpression<MessageBuilder, String>
        implements EasyMultiple<MessageBuilder, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderText.class, String.class,
                "[<stripped>] text", "messagebuilders")
                .setName("Text of Message Builder")
                .setDesc("Get the text inside of a Message Builder. You can set, reset and delete this text")
                .setExample(
                        "command /build:",
                        "\ttrigger:",
                        "\t\tset {e} to a new message builder",
                        "\t\tset text of {e} to \"Hey look at this\"",
                        "\t\tbroadcast \"%text of {e}%\""
                );
    }

    private boolean stripped;

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
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE)) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        change(getExpr().getAll(e), builder -> {
            switch (mode) {
                case RESET:
                case DELETE:
                    builder.setContent(null);
                    break;
                case SET:
                    builder.setContent((String) delta[0]);
            }
        });
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
