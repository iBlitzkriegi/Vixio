package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.MessageBuilder;
import org.bukkit.event.Event;

public class ExprMessageBuilderTts extends SimplePropertyExpression<MessageBuilder, Boolean> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMessageBuilderTts.class, Boolean.class, "tts state", "messagebuilders")
                .setName("TTS of Message Builder")
                .setDesc("Get the TTS state of a Message Builder. Changers: SET")
                .setExample(
                        "command /build",
                        "\ttrigger:",
                        "\t\tset {e} to a new message builder",
                        "\t\tset tts of {e} to false",
                        "\t\tbroadcast \"%tts of {e}%\""
                );
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<MessageBuilder>) exprs[0]);
        return true;
    }

    @Override
    protected String getPropertyName() {
        return "tts state";
    }

    @Override
    public Boolean convert(MessageBuilder messageBuilder) {
        return messageBuilder.build().isTTS();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) && getExpr().isSingle()) {
            return new Class[]{Boolean.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        MessageBuilder builder = getExpr().getSingle(e);
        if (builder == null) return;

        switch (mode) {
            case RESET:
                builder.setTTS(false);
                break;
            case SET:
                builder.setTTS((Boolean) delta[0]);

        }
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the tts state of " + getExpr().toString(e, debug);
    }
}
