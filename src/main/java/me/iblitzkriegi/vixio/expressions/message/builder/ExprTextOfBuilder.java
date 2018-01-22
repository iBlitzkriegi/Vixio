package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.MessageBuilder;
import org.bukkit.event.Event;

public class ExprTextOfBuilder extends SimplePropertyExpression<MessageBuilder, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprTextOfBuilder.class, String.class, "[<stripped>] text", "messagebuilders")
                .setName("Text of Message Builder")
                .setDesc("Get the text inside of a Message Builder")
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
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<MessageBuilder>) exprs[0]);
        stripped = parseResult.regexes.size() == 1;
        return true;
    }
    @Override
    protected String getPropertyName() {
        return "[<stripped>] text of messagebuilders";
    }

    @Override
    public String convert(MessageBuilder messageBuilder) {
        if(stripped){
            try {
                return messageBuilder.isEmpty() ? null : messageBuilder.build().getContentStripped();
            }catch (UnsupportedOperationException x){

            }
        } else {
            return messageBuilder.isEmpty() ? null : messageBuilder.build().getContentRaw();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{String.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        MessageBuilder builder = getExpr().getSingle(e);
        if(builder == null) return;

        switch(mode){
            case RESET:
            case DELETE:
                builder.setContent(null);
                break;
            case SET:
                builder.setContent((String) delta[0]);

        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the text of " + getExpr().toString(e, debug);
    }

}
