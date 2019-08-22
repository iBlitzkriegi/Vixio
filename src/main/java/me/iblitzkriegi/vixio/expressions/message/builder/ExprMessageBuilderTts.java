package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.MessageBuilder;
import org.bukkit.event.Event;

public class ExprMessageBuilderTts extends SimplePropertyExpression<MessageBuilder, Boolean> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMessageBuilderTts.class, Boolean.class,
                "tts state", "messagebuilders")
                .setName("TTS of Message Builder")
                .setDesc("Get the TTS state of a Message Builder. The state can be set to true or false.")
                .setExample(
                        "discord command $tts:",
                        "\ttrigger:",
                        "\t\tset {_} to a new message builder",
                        "\t\tset tts state of {_} to true",
                        "\t\tsend {_} to event-channel with event-bot",
                        "\t\treply with \"done?\""
                );
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
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
        return new MessageBuilder(messageBuilder).setContent("content").build().isTTS();
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) && getExpr().isSingle()) {
            return new Class[]{Boolean.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (MessageBuilder builder : getExpr().getAll(e)) {
            builder.setTTS(mode == Changer.ChangeMode.RESET ? false : (Boolean) delta[0]);
        }
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the tts state of " + getExpr().toString(e, debug);
    }

}
